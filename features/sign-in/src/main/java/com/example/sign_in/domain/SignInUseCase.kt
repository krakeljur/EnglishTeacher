package com.example.sign_in.domain

import com.example.sign_in.domain.repositories.SignInRepository
import javax.inject.Inject


class SignInUseCase @Inject constructor(
    private val signInRepository: SignInRepository
) {

    suspend fun signIn(login: String, password: String) {
        signInRepository.signIn(login, password)
    }
}