package com.example.sign_in.domain

import com.example.sign_in.domain.repositories.SignInRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class IsSignedInUseCase @Inject constructor(
    private val signInRepository: SignInRepository
) {
    fun isSigned(): StateFlow<Boolean> {
        return signInRepository.isSign()
    }
}