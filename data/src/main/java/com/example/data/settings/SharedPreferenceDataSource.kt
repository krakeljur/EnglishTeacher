package com.example.data.settings

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SharedPreferenceDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsDataSource, OnSharedPreferenceChangeListener {


    private val preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    private val tokenFlow = MutableStateFlow<String?>(null)

    init {
        preferences.registerOnSharedPreferenceChangeListener(this)
        tokenFlow.value = getToken()

    }

    override fun getToken(): String? {
        return preferences.getString(KEY_TOKEN, null)
    }

    override fun setToken(token: String?) {
        preferences.edit {
            if (token == null)
                remove(KEY_TOKEN)
            else
                putString(KEY_TOKEN, token)
        }
    }

    override fun listenToken(): Flow<String?> = tokenFlow.asStateFlow()

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        tokenFlow.value = getToken()
    }


    companion object {
        const val KEY_TOKEN = "token"
        const val PREFERENCE_NAME = "preference"
    }
}