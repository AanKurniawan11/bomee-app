package com.example.bomeeapp.data.network.auth

import com.example.bomeeapp.data.network.model.LoginModel
import com.example.bomeeapp.data.network.responses.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {
    @Headers("Content-Type: application/json")
    @POST("auth/login")
    suspend fun login(
        @Body info: LoginModel
    ): Response<LoginResponse>
}