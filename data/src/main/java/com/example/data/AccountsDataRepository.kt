package com.example.data

import com.example.common.Container
import com.example.data.accounts.entities.AccountDataEntity
import com.example.data.accounts.entities.SignUpDataEntity
import kotlinx.coroutines.flow.Flow

interface AccountsDataRepository {

    fun getAccount(): Flow<Container<AccountDataEntity>>

    suspend fun setAccountUsername(newName: String)

    suspend fun signIn(login: String, password: String)

    suspend fun signUp(signUpData: SignUpDataEntity)

    suspend fun logOut()

    fun isSign() : Flow<Boolean>

}