package com.example.data.accounts.sources

import com.example.data.accounts.entities.SignUpDataEntity
import com.example.data.accounts.entities.api.LogoutRequestBody
import com.example.data.accounts.entities.api.RenameUserRequestBody
import com.example.data.accounts.entities.api.SignInRequestBody
import com.example.data.accounts.entities.api.SignUpRequestBody
import com.example.data.base.RetrofitConfig
import javax.inject.Inject

class RetrofitAccountsDataSource @Inject constructor(
    retrofitConfig: RetrofitConfig
) : AccountsDataSource {

    private val accountsApi = retrofitConfig.retrofit.create(AccountsApi::class.java)

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

}