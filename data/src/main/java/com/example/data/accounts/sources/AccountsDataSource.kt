package com.example.data.accounts.sources

import com.example.data.accounts.entity.AccountDataEntity
import com.example.data.accounts.entity.SignUpDataEntity

interface AccountsDataSource {

    suspend fun signIn(login : String, password: String) : Pair<String, AccountDataEntity>

    suspend fun setAccountUsername(accountWithNewName: AccountDataEntity)

    suspend fun signUp(signUpDataEntity: SignUpDataEntity)

    suspend fun logout()
}