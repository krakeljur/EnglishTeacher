package com.example.profile.domain

import androidx.paging.PagingData
import com.example.profile.domain.entities.GameResult
import com.example.profile.domain.repositories.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStatisticUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {

    fun getStatistic(): Flow<PagingData<GameResult>> {
        return profileRepository.getStatistic()
    }
}