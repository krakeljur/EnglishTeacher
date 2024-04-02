package com.example.redactor.domain

import com.example.common.Container
import com.example.redactor.domain.entities.WordEntity
import com.example.redactor.domain.repositories.RedactorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWordsUseCase @Inject constructor(
    private val repository: RedactorRepository
) {

    suspend fun updateWords(idLesson: String) {
        repository.updateWords(idLesson)
    }

    fun getWords(): Flow<Container<List<WordEntity>>> = repository.getWords()
}