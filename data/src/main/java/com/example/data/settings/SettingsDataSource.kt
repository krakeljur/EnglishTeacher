package com.example.data.settings

import kotlinx.coroutines.flow.Flow

interface SettingsDataSource {

    fun getToken() : String?

    fun setToken(token: String?)

    fun listenToken() : Flow<String?>
}