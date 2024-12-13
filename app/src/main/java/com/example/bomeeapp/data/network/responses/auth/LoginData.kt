package com.example.bomeeapp.data.network.responses.auth

data class LoginData(
    val token: String,
    val refreshToken: String,
    val role: String
)
