package com.example.catalog.domain

import com.example.catalog.domain.repositories.LessonRepository
import javax.inject.Inject

class UpdateFlowsUseCase @Inject constructor(
    private val lessonRepository: LessonRepository
) {

    suspend fun update() {
        lessonRepository.update()
    }
}