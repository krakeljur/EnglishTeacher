package com.example.data.catalog.sources

import com.example.data.catalog.entities.LessonDataEntity
import com.example.data.catalog.entities.WordDataEntity

interface CatalogDataSource {


    fun getCatalog(): List<LessonDataEntity>

    fun getFavorite(): List<LessonDataEntity>

    suspend fun addFavorite(idLesson: Long)

    suspend fun deleteFavorite(idLesson: Long)

    fun getWords(idLesson: Long): List<WordDataEntity>


}