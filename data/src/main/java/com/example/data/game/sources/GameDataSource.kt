package com.example.data.game.sources

import com.example.data.catalog.entities.WordDataEntity
import com.example.data.game.etities.ResultGameEntity

interface GameDataSource {


    suspend fun setResultGame(resultGameEntity: ResultGameEntity)

    fun getWords(idLesson: Long) : List<WordDataEntity>

    fun getResults() : List<ResultGameEntity>
}