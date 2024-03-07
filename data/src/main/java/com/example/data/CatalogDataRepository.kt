package com.example.data

import androidx.paging.PagingData
import com.example.data.catalog.entities.LessonDataEntity
import com.example.data.catalog.entities.WordDataEntity
import kotlinx.coroutines.flow.Flow

interface CatalogDataRepository {

    fun getCatalog(): Flow<PagingData<LessonDataEntity>>

    fun getFavorite(): Flow<PagingData<LessonDataEntity>>

    suspend fun addFavorite(idLesson: String)

    suspend fun deleteFavorite(idLesson: String)

    fun getWords(idLesson: Long): Flow<PagingData<WordDataEntity>>


}