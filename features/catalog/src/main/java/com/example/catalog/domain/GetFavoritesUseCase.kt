package com.example.catalog.domain

import androidx.paging.PagingData
import com.example.catalog.domain.entities.LessonData
import com.example.catalog.domain.repositories.LessonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val lessonRepository: LessonRepository
) {

    fun getFavorites() : Flow<PagingData<LessonData>> {
        return lessonRepository.getFavorite()
    }

}