package com.example.data

import com.example.data.catalog.entities.WordDataEntity

interface LessonDataRepository {

    suspend fun createLesson(name: String, description: String)

    suspend fun addWord(word: WordDataEntity, idLesson: String)

    suspend fun deleteWord(word: WordDataEntity, idLesson: String)

    suspend fun deleteLesson(idLesson: String)

    suspend fun patchLesson(newName: String, newDescription: String, idLesson: String)


}