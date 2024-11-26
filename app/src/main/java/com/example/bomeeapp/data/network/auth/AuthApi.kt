package com.example.bomeeapp.data.network.auth

import com.example.bomeeapp.data.network.model.LoginModel
import com.example.bomeeapp.data.network.responses.LoginResponse
import com.example.bomeeapp.data.network.responses.LogoutResponse
import com.example.bomeeapp.data.network.responses.UserDataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApi {
    @Headers("Content-Type: application/json")
    @POST("auth/login")
    suspend fun login(
        @Body info: LoginModel
    ): Response<LoginResponse>

    @GET("users/current")
    suspend fun getUserData(
        @Header("Authorization") token: String
    ): Response<UserDataResponse>

    @DELETE("auth/logout/{refreshToken}")
    suspend fun logout(
        @Path("refreshToken") refreshToken: String,
        @Header("Authorization") token: String

    ): Response<LogoutResponse>



}