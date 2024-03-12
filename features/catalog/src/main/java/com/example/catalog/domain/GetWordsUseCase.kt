package com.example.catalog.domain

import com.example.catalog.domain.entities.WordData
import com.example.catalog.domain.repositories.LessonRepository
import com.example.common.Container
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWordsUseCase @Inject constructor(
    private val lessonRepository: LessonRepository
) {

    fun getWords() : Flow<Container<List<WordData>>> =
        lessonRepository.getWords()

    suspend fun updateWords(idLesson : String) {
        lessonRepository.updateWords(idLesson)
    }

}