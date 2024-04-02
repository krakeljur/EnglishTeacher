package com.example.redactor.domain.repositories

import androidx.paging.PagingData
import com.example.common.Container
import com.example.redactor.domain.entities.ResultEntity
import com.example.redactor.domain.entities.WordEntity
import kotlinx.coroutines.flow.Flow

interface RedactorRepository {

    fun getWords(): Flow<Container<List<WordEntity>>>

    suspend fun addWord(rus: String, eng: String, idLesson: String)

    suspend fun deleteWord(wordEntity: WordEntity, idLesson: String)

    suspend fun updateWords(idLesson: String)

    suspend fun patchLesson(newName: String, newDescription: String, idLesson: String)

    fun getStatistic(idLesson: String): Flow<PagingData<ResultEntity>>
}