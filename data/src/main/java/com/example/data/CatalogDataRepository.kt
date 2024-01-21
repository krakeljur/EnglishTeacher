package com.example.data

import com.example.common.Container
import com.example.data.catalog.entities.LessonDataEntity
import com.example.data.catalog.entities.WordDataEntity
import kotlinx.coroutines.flow.Flow

interface CatalogDataRepository {

    fun getCatalog(): Flow<Container<List<LessonDataEntity>>>

    fun getFavorite(): Flow<Container<List<LessonDataEntity>>>

    suspend fun addFavorite(idLesson: Long)

    suspend fun deleteFavorite(idLesson: Long)

    fun getWords(idLesson: Long): Flow<Container<List<WordDataEntity>>>

}