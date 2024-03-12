package com.example.catalog.domain

import com.example.catalog.domain.entities.LessonData
import com.example.catalog.domain.repositories.LessonRepository
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val lessonRepository: LessonRepository
) {

    suspend fun deleteFavorite(lessonData: LessonData) {
        lessonRepository.deleteFavorite(lessonData)
    }
}