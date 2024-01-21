package com.example.sign_up.domain

import com.example.sign_up.domain.entities.SignUpData
import com.example.sign_up.domain.repositories.SignUpRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) {

    suspend fun signUp(signUpData: SignUpData) {
        signUpRepository.signUp(signUpData)
    }
}