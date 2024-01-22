package com.example.catalog.domain

import com.example.catalog.domain.repositories.LessonRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val lessonRepository: LessonRepository
) {

    suspend fun addFavorite(id: Long) {
        lessonRepository.addFavorite(id)
    }
}