package com.example.bomeeapp.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.bomeeapp.ui.Utils.handleApiError
import com.example.bomeeapp.data.network.Resource
import com.example.bomeeapp.databinding.ActivityLoginBinding
import com.example.bomeeapp.ui.HomeActivity
import com.example.bomeeapp.ui.Utils.startNewActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isLogin()) {
            startNewActivity(HomeActivity::class.java)
        }

        loginViewModel.loginResponse.observe(this) { response ->
            when (response) {
                is Resource.Loading -> {
                    // Show the loading spinner
                    binding.loadingProgressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    // Hide the loading spinner and handle success
                    binding.loadingProgressBar.visibility = View.GONE
                    startNewActivity(HomeActivity::class.java)
                    loginViewModel.saveUsername(binding.txfUsername.text.toString())
                    loginViewModel.savePassword(binding.passwordEditText.text.toString())
                    loginViewModel.saveAccessToken(response.value.data.token)
                }

                is Resource.Failure -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    handleApiError(binding.root, response)
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.txfUsername.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                loginViewModel.login(username, password)
            } else {
                Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun isLogin(): Boolean {
        val token = loginViewModel.getToken()
        return token.isNotEmpty()
    }
}
