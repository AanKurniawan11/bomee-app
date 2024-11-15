package com.example.bomeeapp.data.network.auth

import com.example.bomeeapp.data.local.UserPreferences
import com.example.bomeeapp.data.network.BaseRepository
import com.example.bomeeapp.data.network.model.LoginModel
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
}
