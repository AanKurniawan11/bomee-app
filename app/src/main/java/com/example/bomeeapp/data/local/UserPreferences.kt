package com.example.bomeeapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "bomee_app_data_store")

class UserPreferences @Inject constructor(@ApplicationContext context: Context){

    private val appContext = context.applicationContext

    val accessToken: Flow<String>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[TOKEN] ?: ""
        }

    suspend fun saveAccessToken(accessToken: String) {
        appContext.dataStore.edit { preferences ->
            preferences[TOKEN] = accessToken
        }
    }

    fun getAccessToken() = runBlocking(Dispatchers.IO) {
        accessToken.first()
    }

    val username: Flow<String>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[USERNAME] ?: ""
        }

    suspend fun saveUsername(username: String) {
        appContext.dataStore.edit { preferences ->
            preferences[USERNAME] = username
        }
    }


    fun getUsername() = runBlocking(Dispatchers.IO) {
        username.first()
    }

    val password: Flow<String>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[PASSWORD] ?: ""
        }

    suspend fun savePassword(password: String) {
        appContext.dataStore.edit { preferences ->
            preferences[PASSWORD] = password
        }
    }

    fun getPassword() = runBlocking(Dispatchers.IO) {
        password.first()
    }



    companion object {
        private val TOKEN = stringPreferencesKey("token")
        private val USERNAME = stringPreferencesKey("username")
        private val PASSWORD = stringPreferencesKey("password")
    }

}
