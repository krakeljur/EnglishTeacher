package com.example.profile.domain.repositories

import androidx.paging.PagingData
import com.example.profile.domain.entities.Lesson
import kotlinx.coroutines.flow.Flow

interface LessonRepository {

    suspend fun createLesson(name: String, description: String)

    suspend fun deleteLesson(idLesson: String)

    fun getMyLessons() : Flow<PagingData<Lesson>>
}