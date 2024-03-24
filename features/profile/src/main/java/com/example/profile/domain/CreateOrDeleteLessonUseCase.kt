package com.example.profile.domain

import com.example.profile.domain.repositories.LessonRepository
import javax.inject.Inject

class CreateOrDeleteLessonUseCase @Inject constructor(
    private val lessonRepository: LessonRepository
) {

    suspend fun createLesson(name: String, description: String) {
        lessonRepository.createLesson(name, description)
    }

    suspend fun deleteLesson(idLesson: String) {
        lessonRepository.deleteLesson(idLesson)
    }
}