package com.example.data.accounts

import com.example.common.Container
import com.example.data.AccountsDataRepository
import com.example.data.accounts.entity.AccountDataEntity
import com.example.data.accounts.sources.AccountsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class AccountsDataRepositoryImpl @Inject constructor(
    private val source: AccountsDataSource
) : AccountsDataRepository {

    private val accountFlow: MutableStateFlow<Container<AccountDataEntity>> =
        MutableStateFlow(Container.Pending)

    override fun getAccount(id: Long): Flow<Container<AccountDataEntity>> {
        return accountFlow.asStateFlow()
    }

    override suspend fun setAccountUsername(newName: String) {
        val oldAccount = accountFlow.value as AccountDataEntity
        val newAccount = AccountDataEntity(
            oldAccount.id,
            newName,
            oldAccount.login
        )

        source.setAccountUsername(newAccount)

        accountFlow.emit(Container.Success(newAccount))
    }

    override suspend fun signIn(login: String, password: String): String {
        val (token, account) = source.signIn(login, password)
        accountFlow.emit(Container.Success(account))
        return token
    }
}