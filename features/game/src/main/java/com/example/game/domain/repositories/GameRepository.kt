package com.example.game.domain.repositories

import com.example.common.Container
import com.example.game.domain.entities.ResultGame
import com.example.game.domain.entities.WordEntity
import kotlinx.coroutines.flow.Flow

interface GameRepository {


    suspend fun setResult(resultGame: ResultGame)

    fun getWords(): Flow<Container<List<WordEntity>>>

    suspend fun updateWords(idLesson: String)
}