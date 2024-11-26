package com.example.bomeeapp.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.bomeeapp.ui.Utils.handleApiError
import com.example.bomeeapp.data.network.Resource
import com.example.bomeeapp.databinding.ActivityLoginBinding
import com.example.bomeeapp.ui.HomeActivity
import com.example.bomeeapp.ui.Utils.startNewActivity
import com.example.bomeeapp.data.local.UserPreferences
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this)

//        if (isLogin()) {
//            startNewActivity(HomeActivity::class.java)
//            finish()
//        }

        loginViewModel.loginResponse.observe(this) { response ->
            when (response) {
                is Resource.Loading -> {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    startNewActivity(HomeActivity::class.java)
                    loginViewModel.saveAccessToken(response.value.data.token)
                    loginViewModel.saveRefreshToken(response.value.data.refreshToken)
                }

                is Resource.Failure -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    if (response.errorCode == 403) {
                        loginViewModel.clearAccessToken()
                        Toast.makeText(
                            this,
                            "Session expired. Please log in again.",
                            Toast.LENGTH_SHORT
                        ).show()
                        startNewActivity(LoginActivity::class.java) // Arahkan ke login
                    } else {
                        handleApiError(binding.root, response)
                    }
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

