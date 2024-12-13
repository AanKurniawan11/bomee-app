package com.example.bomeeapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bomeeapp.data.local.UserPreferences
import com.example.bomeeapp.data.network.Resource
import com.example.bomeeapp.data.network.auth.AuthRepository
import com.example.bomeeapp.data.network.responses.auth.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {


    private var _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>> get() = _loginResponse

    fun login(username: String, password: String) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = authRepository.login(username, password)
    }

    fun getToken(): String {
        return userPreferences.getAccessToken()
    }

    fun saveAccessToken(token: String) {
        viewModelScope.launch {
            userPreferences.saveAccessToken(token)
        }


    }

    fun saveRefreshToken(refreshToken: String) {
        viewModelScope.launch {
            userPreferences.saveRefreshToken(refreshToken)
        }
    }

    fun clearAccessToken() {
        viewModelScope.launch {
            userPreferences.clearAccessToken()
        }
    }


}