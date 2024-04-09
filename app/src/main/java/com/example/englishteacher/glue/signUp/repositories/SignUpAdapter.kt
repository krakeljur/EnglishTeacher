package com.example.englishteacher.glue.signUp.repositories

import com.example.data.AccountsDataRepository
import com.example.data.accounts.entities.SignUpDataEntity
import com.example.sign_up.domain.entities.SignUpData
import com.example.sign_up.domain.repositories.SignUpRepository
import javax.inject.Inject

class SignUpAdapter @Inject constructor(
    private val accountsDataRepository: AccountsDataRepository
) : SignUpRepository {
    override suspend fun signUp(signUpData: SignUpData) {
        accountsDataRepository.signUp(
            SignUpDataEntity(
                signUpData.name,
                signUpData.login,
                signUpData.password
            )
        )
    }
}