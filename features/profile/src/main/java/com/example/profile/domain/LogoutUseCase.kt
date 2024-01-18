package com.example.profile.domain

import com.example.profile.domain.repositories.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend fun logout() {
        authRepository.logout()
    }
}