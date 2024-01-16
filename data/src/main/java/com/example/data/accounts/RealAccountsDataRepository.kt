package com.example.data.accounts

import com.example.common.Container
import com.example.data.AccountsDataRepository
import com.example.data.accounts.entity.AccountDataEntity
import com.example.data.accounts.entity.SignUpDataEntity
import kotlinx.coroutines.flow.Flow

class RealAccountsDataRepository(

) : AccountsDataRepository {
    override fun getAccount(): Flow<Container<AccountDataEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun setAccountName(name: String) {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(login: String, password: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun signUp(signUpData: SignUpDataEntity) {
        TODO("Not yet implemented")
    }

    override fun reload() {
        TODO("Not yet implemented")
    }
}