package com.example.bomeeapp.ui.history

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.example.bomeeapp.data.network.Resource
import com.example.bomeeapp.databinding.ActivityHistoryBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private val viewModel: HistoryViewModel by viewModels()
    private lateinit var historyAllAdapter : HistoryAllAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.getHistoryAll()


        historyAllAdapter = HistoryAllAdapter()
        binding.rvHistoryAll.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity, VERTICAL, false)
            adapter = historyAllAdapter
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        historyAllAdapter.setOnCancelClickListener { bookingId ->
            showCancelReasonDialog(bookingId)
        }
        viewModel.historyAll.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    val historyAll = resource.value.data
                    historyAllAdapter.setData(historyAll)
                }

                is Resource.Failure -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    val errorMessage = resource.errorBody?.string() ?: "Unknown error"
                    Log.e("HistoryActivity", "Error: $errorMessage")
                }


            }
        }

        viewModel.cancelBooking.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    Toast.makeText(this, "Booking canceled successfully", Toast.LENGTH_SHORT).show()
                    viewModel.getHistoryAll() // Refresh data
                }
                is Resource.Failure -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    val errorMessage = resource.errorBody?.string() ?: "Unknown error"
                    Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun showCancelReasonDialog(bookingId: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cancel Booking")

        val input = EditText(this)
        input.hint = "Enter cancel reason"
        builder.setView(input)

        builder.setPositiveButton("Submit") { dialog, _ ->
            val reason = input.text.toString().trim()
            if (reason.isNotEmpty()) {
                viewModel.cancelBooking(bookingId, reason)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Reason cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }
}