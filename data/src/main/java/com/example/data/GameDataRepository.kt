package com.example.data

import com.example.common.Container
import com.example.data.catalog.entities.WordDataEntity
import com.example.data.game.etities.ResultGameEntity
import kotlinx.coroutines.flow.Flow

interface GameDataRepository {

    suspend fun setResult(resultGame : ResultGameEntity)

    fun getWords(idLesson: Long) : Flow<Container<List<WordDataEntity>>>

    fun getResults() : Flow<Container<List<ResultGameEntity>>>

}