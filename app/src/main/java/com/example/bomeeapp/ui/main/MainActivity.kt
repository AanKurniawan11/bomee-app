package com.example.bomeeapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bomeeapp.R
import com.example.bomeeapp.databinding.ActivityMainBinding
import com.example.bomeeapp.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Handler (Looper.getMainLooper()).postDelayed({
            goToLogin()
        },2000)
    }
    private fun goToLogin(){
        Intent(this, LoginActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }


    }
