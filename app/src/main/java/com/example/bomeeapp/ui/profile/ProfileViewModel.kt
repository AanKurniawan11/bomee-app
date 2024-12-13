package com.example.bomeeapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bomeeapp.data.local.UserPreferences
import com.example.bomeeapp.data.network.Resource
import com.example.bomeeapp.data.network.auth.AuthRepository
import com.example.bomeeapp.data.network.responses.LogoutResponse
import com.example.bomeeapp.data.network.responses.profile.UserData
import com.example.bomeeapp.data.network.responses.profile.UserDataResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private var _userData: MutableLiveData<Resource<UserDataResponse>> = MutableLiveData()
    val userData: LiveData<Resource<UserDataResponse>> get() = _userData

    private var _logoutResponse: MutableLiveData<Resource<LogoutResponse>> = MutableLiveData()
    val logout : LiveData<Resource<LogoutResponse>> get() = _logoutResponse

    fun getUserData() = viewModelScope.launch {
        _userData.value = Resource.Loading
        _userData.value = authRepository.getUserData()
    }

    fun performLogout() = viewModelScope.launch {
        _logoutResponse.value = Resource.Loading
        _logoutResponse.value = authRepository.logout()
    }


    fun getUserName(userData: UserData): String {
        return userData.name ?: "No name available"
    }

    fun getUsername(userData: UserData): String {
        return userData.username ?: "No username available"
    }
}

