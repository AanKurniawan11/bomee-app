package com.example.bomeeapp.data.network.responses

data class LoginResponse(
    val code: Int,
    val status: String,
    val message: String,
    val data: LoginData
)
