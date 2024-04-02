package com.example.redactor.domain

import com.example.redactor.domain.entities.WordEntity
import com.example.redactor.domain.repositories.RedactorRepository
import javax.inject.Inject

class RedactWordUseCase @Inject constructor(
    private val repository: RedactorRepository
) {


    suspend fun addWord(rus: String, eng: String, idLesson: String) {
        repository.addWord(rus, eng, idLesson)
    }

    suspend fun deleteWord(wordEntity: WordEntity, idLesson: String) {
        repository.deleteWord(wordEntity, idLesson)
    }
}