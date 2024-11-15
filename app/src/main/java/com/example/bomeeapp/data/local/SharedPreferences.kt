package com.example.bomeeapp.data.local

import android.content.Context

object SharedPreferences {
        private const val PREFS_NAME = "my_shared_prefs"
        private const val TOKEN = "token"

        fun saveToken(context: Context, token: String) {
            val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            sharedPrefs.edit().putString(TOKEN, token).apply()
        }

        fun getToken(context: Context): String? {
            val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            return sharedPrefs.getString(TOKEN, null)
        }
    }
