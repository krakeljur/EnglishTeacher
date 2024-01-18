package com.example.data.accounts.sources

import com.example.data.accounts.entity.AccountDataEntity

interface AccountsDataSource {

    suspend fun signIn(login : String, password: String) : Pair<String, AccountDataEntity>

    suspend fun getAccount(id: Long): AccountDataEntity

    suspend fun setAccountUsername(accountWithNewName: AccountDataEntity)
}