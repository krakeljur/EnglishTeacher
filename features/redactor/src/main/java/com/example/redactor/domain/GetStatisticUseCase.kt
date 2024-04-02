package com.example.redactor.domain

import androidx.paging.PagingData
import com.example.redactor.domain.entities.ResultEntity
import com.example.redactor.domain.repositories.RedactorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStatisticUseCase @Inject constructor(
    private val repository: RedactorRepository
) {

    fun getStatistic(idLesson: String): Flow<PagingData<ResultEntity>> =
        repository.getStatistic(idLesson)
}