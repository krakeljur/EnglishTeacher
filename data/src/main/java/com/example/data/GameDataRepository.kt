package com.example.data

import androidx.paging.PagingData
import com.example.data.game.etities.ResultGameEntity
import kotlinx.coroutines.flow.Flow

interface GameDataRepository {

    suspend fun setResult(resultGame: ResultGameEntity)
    fun getResults(idLesson: String = ""): Flow<PagingData<ResultGameEntity>>

}