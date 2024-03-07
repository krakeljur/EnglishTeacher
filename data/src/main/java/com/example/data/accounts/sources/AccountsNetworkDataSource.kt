package com.example.data.accounts.sources

import com.example.data.accounts.entities.AccountDataEntity
import com.example.data.accounts.entities.SignUpDataEntity

interface AccountsNetworkDataSource {

    suspend fun signIn(login : String, password: String) : String

    suspend fun setAccountUsername(token: String, newName: String)

    suspend fun signUp(signUpDataEntity: SignUpDataEntity)

    suspend fun logout(token: String)

    suspend fun getAccount(token: String) : AccountDataEntity

}