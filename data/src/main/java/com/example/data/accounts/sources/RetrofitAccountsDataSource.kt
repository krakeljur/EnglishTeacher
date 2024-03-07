package com.example.data.accounts.sources

import com.example.data.accounts.entities.AccountDataEntity
import com.example.data.accounts.entities.SignUpDataEntity
import com.example.data.accounts.entities.api.GetAccountRequestBody
import com.example.data.accounts.entities.api.LogoutRequestBody
import com.example.data.accounts.entities.api.RenameUserRequestBody
import com.example.data.accounts.entities.api.SignInRequestBody
import com.example.data.accounts.entities.api.SignUpRequestBody
import com.example.data.accounts.sources.api.AccountsApi
import javax.inject.Inject

class RetrofitAccountsDataSource @Inject constructor(
    private val accountsApi: AccountsApi
) : AccountsNetworkDataSource {

    override suspend fun signIn(login: String, password: String): String {
        val signInRequestBody = SignInRequestBody(login, password)
        return accountsApi.singIn(signInRequestBody).token
    }

    override suspend fun setAccountUsername(token: String, newName: String) {
        val renameUserRequestBody = RenameUserRequestBody(token, newName)
        accountsApi.renameUser(renameUserRequestBody)
    }


    override suspend fun signUp(signUpDataEntity: SignUpDataEntity) {
        val signUpRequestBody = SignUpRequestBody(
            signUpDataEntity.name,
            signUpDataEntity.login,
            signUpDataEntity.pass
        )
        accountsApi.signUp(signUpRequestBody)
    }

    override suspend fun logout(token: String) {
        val logoutRequestBody = LogoutRequestBody(token)
        accountsApi.logout(logoutRequestBody)
    }

    override suspend fun getAccount(token: String): AccountDataEntity {
        return accountsApi.getAccount(GetAccountRequestBody(token)).toAccountDataEntity()
    }

}