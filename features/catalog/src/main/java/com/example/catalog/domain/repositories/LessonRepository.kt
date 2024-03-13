package com.example.catalog.domain.repositories

import androidx.paging.PagingData
import com.example.catalog.domain.entities.LessonData
import com.example.catalog.domain.entities.WordData
import com.example.common.Container
import kotlinx.coroutines.flow.Flow

interface LessonRepository {

    fun getCatalog(isFavorite: Boolean, searchBy: String): Flow<PagingData<LessonData>>

    suspend fun addFavorite(lesson: LessonData)

    suspend fun deleteFavorite(lesson: LessonData)

    fun getWords() : Flow<Container<List<WordData>>>

    suspend fun updateWords(idLesson: String)


}