package com.example.data.accounts

import com.example.common.Container
import com.example.data.AccountsDataRepository
import com.example.data.accounts.entity.AccountDataEntity
import com.example.data.accounts.entity.SignUpDataEntity
import com.example.data.accounts.sources.AccountsDataSource
import com.example.data.settings.SettingsDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountsDataRepositoryImpl @Inject constructor(
    private val sourceAccounts: AccountsDataSource,
    private val sourceSettings: SettingsDataSource,
    coroutineScope: CoroutineScope
) : AccountsDataRepository {


    private val accountFlow: MutableStateFlow<Container<AccountDataEntity>> =
        MutableStateFlow(Container.Error("AuthException"))

    private var token: String? = null

    init {
        coroutineScope.launch {
            sourceSettings.listenToken().collect {
                if (it == null)
                    accountFlow.emit(Container.Error("AuthException"))
                token = it
            }
        }
    }

    override fun getAccount(): Flow<Container<AccountDataEntity>> {
        return accountFlow.asStateFlow()
    }

    override suspend fun setAccountUsername(newName: String) {


        val oldAccount = accountFlow.value as Container.Success

        accountFlow.emit(Container.Pending)

        val newAccount = AccountDataEntity(
            oldAccount.data.id,
            newName,
            oldAccount.data.login
        )
        try {
            sourceAccounts.setAccountUsername(newAccount)
            accountFlow.emit(Container.Success(newAccount))
        } catch (_: Exception) {
            accountFlow.emit(Container.Error("Bad account"))
        }
    }

    override suspend fun signIn(login: String, password: String) {
        accountFlow.emit(Container.Pending)

        try {
            val (token, account) = sourceAccounts.signIn(login, password)
            accountFlow.emit(Container.Success(account))
            sourceSettings.setToken(token)
        } catch (_: Exception) {
            accountFlow.emit(Container.Error("sign-in error"))
        }

    }

    override suspend fun signUp(signUpData: SignUpDataEntity) {
        sourceAccounts.signUp(signUpData)
    }

    override suspend fun logOut() {
        sourceAccounts.logout()
        sourceSettings.setToken(null)
    }
}