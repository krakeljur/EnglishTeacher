package com.example.sign_in.domain

import com.example.sign_in.domain.repositories.SignInRepository
import javax.inject.Inject

class IsSignedInUseCase @Inject constructor(
    private val signInRepository: SignInRepository
) {
    fun isSigned(): Boolean = signInRepository.isSign()
}