package com.example.bomeeapp.data.network.auth

import android.util.Log
import com.example.bomeeapp.data.local.UserPreferences
import com.example.bomeeapp.data.network.BaseRepository
import com.example.bomeeapp.data.network.Resource
import com.example.bomeeapp.data.network.model.LoginModel
import com.example.bomeeapp.data.network.responses.LogoutResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApi,
    private val userPreferences: UserPreferences
) : BaseRepository() {

    suspend fun login(
        username: String,
        password: String
    ) = safeApiCall(
        {
            api.login(LoginModel(username, password))
        },
        userPreferences
    )

    suspend fun getUserData() = safeApiCall({
        val token = userPreferences.getAccessToken()
        api.getUserData("Bearer $token")
    },
        userPreferences
    )
    suspend fun logout(): Resource<LogoutResponse> {
        val refreshToken = userPreferences.getRefreshToken()
        val accessToken = userPreferences.getAccessToken()
        Log.d("AuthRepository", "Using refresh token: $refreshToken")
        return safeApiCall({
            api.logout(refreshToken, "Bearer $accessToken")
        }, userPreferences)
    }




}



