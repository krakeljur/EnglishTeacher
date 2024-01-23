package com.example.catalog.domain.repositories

import com.example.catalog.domain.entities.LessonData
import com.example.common.Container
import kotlinx.coroutines.flow.Flow

interface LessonRepository {

    fun getLesson(lessonId: Long): Flow<Container<LessonData>>

    fun getCatalog() : Flow<Container<List<LessonData>>>

    fun getFavorite() : Flow<Container<List<LessonData>>>

    suspend fun addFavorite(idLesson: Long)

    suspend fun deleteFavorite(idLesson: Long)

    suspend fun update()

}