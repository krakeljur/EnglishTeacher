package com.example.profile.domain

import com.example.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class EditNameUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend fun editName(newName: String) {
        profileRepository.setUsername(newName)
    }
}