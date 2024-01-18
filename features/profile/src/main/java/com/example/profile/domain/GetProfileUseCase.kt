package com.example.profile.domain

import com.example.common.Container
import com.example.profile.domain.entities.Profile
import com.example.profile.domain.repositories.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    fun getAccount(): Flow<Container<Profile>> = profileRepository.getAccount()

}