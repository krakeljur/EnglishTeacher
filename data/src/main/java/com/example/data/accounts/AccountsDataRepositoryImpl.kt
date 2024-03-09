package com.example.data.accounts

import com.example.common.Container
import com.example.data.AccountsDataRepository
import com.example.data.accounts.entities.AccountDataEntity
import com.example.data.accounts.entities.SignUpDataEntity
import com.example.data.accounts.entities.api.GetAccountRequestBody
import com.example.data.accounts.entities.api.LogoutRequestBody
import com.example.data.accounts.entities.api.RenameUserRequestBody
import com.example.data.accounts.entities.api.SignInRequestBody
import com.example.data.accounts.entities.api.SignUpRequestBody
import com.example.data.accounts.sources.api.AccountsApi
import com.example.data.settings.SettingsDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountsDataRepositoryImpl @Inject constructor(
    private val accountsApi: AccountsApi,
    private val sourceSettings: SettingsDataSource,
    coroutineScope: CoroutineScope
) : AccountsDataRepository {


    private val accountFlow: MutableStateFlow<Container<AccountDataEntity>> =
        MutableStateFlow(Container.Error("AuthException"))

    private var token: String? = sourceSettings.getToken()

    init {
        coroutineScope.launch {
            if (token != null) {
                val account = accountsApi.getAccount(GetAccountRequestBody(token!!))
                sourceSettings.setAccount(
                    AccountDataEntity(
                        account.id,
                        account.name,
                        account.login
                    )
                )
            }
            sourceSettings.listenToken().collect {
                if (it == null)
                    sourceSettings.setAccount(null)
                else {
                    val account = accountsApi.getAccount(GetAccountRequestBody(token!!))
                    sourceSettings.setAccount(
                        AccountDataEntity(
                            account.id,
                            account.name,
                            account.login
                        )
                    )
                }
                token = it
            }
        }
        coroutineScope.launch {
            sourceSettings.listenAccount().collect {
                if (it == null)
                    accountFlow.emit(Container.Error("AuthException"))
                else
                    accountFlow.emit(Container.Success(it))
            }
        }
    }

    override fun getAccount(): Flow<Container<AccountDataEntity>> = accountFlow.asStateFlow()

    override suspend fun setAccountUsername(newName: String) {


        val oldAccount = accountFlow.value as Container.Success

        accountFlow.emit(Container.Pending)

        val newAccount = AccountDataEntity(
            oldAccount.data.id,
            newName,
            oldAccount.data.login
        )
        try {
            accountsApi.renameUser(RenameUserRequestBody(token!!, newName))
            sourceSettings.setAccount(newAccount)
        } catch (e: Exception) {
            accountFlow.emit(Container.Error("Bad account"))
        }
    }

    override suspend fun signIn(login: String, password: String) {
        accountFlow.emit(Container.Pending)

        try {
            val token = accountsApi.singIn(SignInRequestBody(login, password)).token
            sourceSettings.setToken(token)
        } catch (_: Exception) {
            accountFlow.emit(Container.Error("sign-in error"))
        }

    }

    override suspend fun signUp(signUpData: SignUpDataEntity) {
        accountsApi.signUp(
            SignUpRequestBody(
                signUpData.name,
                signUpData.login,
                signUpData.pass
            )
        )
    }

    override suspend fun logOut() {
        accountsApi.logout(LogoutRequestBody(token!!))
        sourceSettings.setToken(null)
    }

    override fun isSign(): Flow<Boolean> {
        return sourceSettings.listenToken().mapLatest {
            it != null
        }
    }
}