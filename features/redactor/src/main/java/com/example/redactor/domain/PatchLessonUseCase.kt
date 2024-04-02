package com.example.redactor.domain

import com.example.redactor.domain.repositories.RedactorRepository
import javax.inject.Inject

class PatchLessonUseCase @Inject constructor(
    private val repository: RedactorRepository
) {

    suspend fun patchLesson(newName: String, newDescription: String, idLesson: String) {
        repository.patchLesson(newName, newDescription, idLesson)
    }
}