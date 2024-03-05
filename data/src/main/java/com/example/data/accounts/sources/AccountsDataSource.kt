package com.example.data.accounts.sources

import com.example.data.accounts.entities.SignUpDataEntity

interface AccountsDataSource {

    suspend fun signIn(login : String, password: String) : String

    suspend fun setAccountUsername(token: String, newName: String)

    suspend fun signUp(signUpDataEntity: SignUpDataEntity)

    suspend fun logout(token: String)
}