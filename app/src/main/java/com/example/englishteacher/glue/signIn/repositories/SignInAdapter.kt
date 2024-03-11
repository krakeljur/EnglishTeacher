package com.example.englishteacher.glue.signIn.repositories

import com.example.data.AccountsDataRepository
import com.example.sign_in.domain.repositories.SignInRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInAdapter @Inject constructor(
    private val accountsDataRepository: AccountsDataRepository
) : SignInRepository {
    override suspend fun signIn(login: String, password: String) {
        accountsDataRepository.signIn(login, password)
    }

    override fun isSign(): Flow<Boolean> {
        return accountsDataRepository.isSign()
    }
}