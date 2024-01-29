package com.example.sign_in.domain

import com.example.sign_in.domain.repositories.SignInRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsSignedInUseCase @Inject constructor(
    private val signInRepository: SignInRepository
) {
    fun isSigned(): Flow<Boolean> {
        return signInRepository.isSign()
    }
}