package com.example.data.accounts.sources

import com.example.data.accounts.entities.AccountDataEntity
import com.example.data.accounts.entities.SignUpDataEntity

interface AccountsDataSource {

    suspend fun signIn(login : String, password: String) : Pair<String, AccountDataEntity>

    suspend fun setAccountUsername(accountWithNewName: AccountDataEntity)

    suspend fun signUp(signUpDataEntity: SignUpDataEntity)

    suspend fun logout()
}