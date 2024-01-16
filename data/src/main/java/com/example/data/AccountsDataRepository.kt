package com.example.data

import com.example.common.Container
import com.example.data.accounts.entity.AccountDataEntity
import com.example.data.accounts.entity.SignUpDataEntity
import kotlinx.coroutines.flow.Flow

interface AccountsDataRepository {

    /**
     * Listen current Account.
     * @return infinite flow, always success; errors are delivered to [Container]
     * */
    fun getAccount(): Flow<Container<AccountDataEntity>>

    /**
     * Update the Name of the current user.
     */
    suspend fun setAccountName(name: String)

    /**
     * Login user and get auth token.
     * */
    suspend fun signIn(login: String, password: String) : String

    /**
     * Just create a new Account.
     * @throws AccountAlreadyExistDataException
     * */
    suspend fun signUp(signUpData: SignUpDataEntity)

    /**
     * Reload the flow returned by [getAccount]
     * */
    fun reload()
}