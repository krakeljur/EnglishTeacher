package com.example.catalog.domain.repositories

import androidx.paging.PagingData
import com.example.catalog.domain.entities.LessonData
import kotlinx.coroutines.flow.Flow

interface LessonRepository {

    fun getCatalog(): Flow<PagingData<LessonData>>

    fun getFavorite(): Flow<PagingData<LessonData>>

    suspend fun addFavorite(lesson: LessonData)

    suspend fun deleteFavorite(lesson: LessonData)


}