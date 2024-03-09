package com.example.data

import androidx.paging.PagingData
import com.example.common.Container
import com.example.data.catalog.entities.LessonDataEntity
import com.example.data.catalog.entities.WordDataEntity
import kotlinx.coroutines.flow.Flow

interface CatalogDataRepository {

    fun getCatalog(): Flow<PagingData<LessonDataEntity>>

    fun getFavorite(): Flow<PagingData<LessonDataEntity>>

    suspend fun addFavorite(lesson: LessonDataEntity)

    suspend fun deleteFavorite(lesson: LessonDataEntity)

    fun getWords(): Flow<Container<List<WordDataEntity>>>

    suspend fun updateWords(idLesson: String)


}