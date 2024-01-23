package com.example.profile.domain

import com.example.common.Container
import com.example.profile.domain.entities.GameResult
import com.example.profile.domain.repositories.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStatisticUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    fun getStatistic(): Flow<Container<List<GameResult>>> {
        return profileRepository.getStatistic()
    }
}