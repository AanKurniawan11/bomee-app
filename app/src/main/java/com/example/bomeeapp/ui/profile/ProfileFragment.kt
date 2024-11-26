package com.example.bomeeapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bomeeapp.databinding.FragmentProfileBinding
import com.example.bomeeapp.data.network.Resource
import com.example.bomeeapp.databinding.LogoutAlertBinding
import com.example.bomeeapp.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        // Get user data
        viewModel.getUserData()
        viewModel.userData.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.loadingProgressBar.visibility = View.GONE

                    val userData = resource.value.data // Access user data here
                    if (userData != null) {
                        // Set name and username to TextViews
                        binding.tvName.text = userData.name
                        binding.tvUsername.text = userData.username
                    } else {
                        // Handle null userData (if necessary)
                        binding.tvName.text = "No name available"
                        binding.tvUsername.text = "No username available"
                    }
                }

                is Resource.Failure -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    val errorMessage = resource.errorBody?.string() ?: "Unknown error"
                    Log.e("ProfileFragment", "Error: $errorMessage")
                }
            }
        }

        viewModel.logout.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    requireActivity().finish() // Finish the current activity if necessary
                }

                is Resource.Failure -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    val errorMessage = resource.errorBody?.string() ?: "Unknown error"
                    Log.e("ProfileFragment", "Error: $errorMessage")
                }
            }
        }

        binding.btnLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
        return view
    }

    private fun showLogoutConfirmationDialog() {
        val dialogBinding = LogoutAlertBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()
        dialogBinding.btnYes.setOnClickListener {
            dialog.dismiss()
            viewModel.performLogout()
        }

        dialogBinding.btnNo.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
