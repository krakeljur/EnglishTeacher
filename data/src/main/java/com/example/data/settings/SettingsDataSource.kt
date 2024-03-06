package com.example.data.settings

import com.example.data.accounts.entities.AccountDataEntity
import kotlinx.coroutines.flow.Flow

interface SettingsDataSource {

    fun getToken() : String?

    fun setToken(token: String?)

    fun listenToken() : Flow<String?>

    fun setAccount(account: AccountDataEntity?)

    fun listenAccount() : Flow<AccountDataEntity?>
}