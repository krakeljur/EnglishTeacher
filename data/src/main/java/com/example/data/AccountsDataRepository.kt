package com.example.data

import com.example.common.Container
import com.example.data.accounts.entity.AccountDataEntity
import kotlinx.coroutines.flow.Flow

interface AccountsDataRepository {

    fun getAccount(id: Long): Flow<Container<AccountDataEntity>>

    suspend fun setAccountUsername(newName: String)

    suspend fun signIn(login: String, password: String) : String

}