package com.example.data.settings

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.core.content.edit
import com.example.data.accounts.entities.AccountDataEntity
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

    private val accountFlow = MutableStateFlow<AccountDataEntity?>(null)

    init {
        preferences.registerOnSharedPreferenceChangeListener(this)
        tokenFlow.value = getToken()
        accountFlow.value = getAccount()
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
    override fun setAccount(account: AccountDataEntity?) {
        preferences.edit {
            if (account == null) {
                remove(KEY_NAME)
                remove(KEY_LOGIN)
                remove(KEY_ID)
            } else {
                putString(KEY_ID, account.id)
                putString(KEY_LOGIN, account.login)
                putString(KEY_NAME, account.name)
            }
        }
    }

    override fun listenAccount(): Flow<AccountDataEntity?> = accountFlow.asStateFlow()

    private fun getAccount(): AccountDataEntity? {
        val name = preferences.getString(KEY_NAME, null)
        val login = preferences.getString(KEY_LOGIN, null)
        val id = preferences.getString(KEY_ID, null)

        return if (name == null || login == null || id == null)
            null
        else AccountDataEntity(
            id,
            name,
            login
        )
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        tokenFlow.value = getToken()
        accountFlow.value = getAccount()

    }


    companion object {
        const val KEY_TOKEN = "token"
        const val PREFERENCE_NAME = "preference"
        const val KEY_NAME = "name"
        const val KEY_LOGIN = "login"
        const val KEY_ID = "id"
    }
}