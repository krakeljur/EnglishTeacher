package com.example.englishteacher.glue.game.repositories

import com.example.common.Container
import com.example.data.GameDataRepository
import com.example.data.game.etities.ResultGameEntity
import com.example.game.domain.entities.ResultGame
import com.example.game.domain.entities.WordEntity
import com.example.game.domain.repositories.GameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class GameAdapter @Inject constructor(
    private val gameDataRepository: GameDataRepository
) : GameRepository{
    override suspend fun setResult(resultGame: ResultGame) {
        gameDataRepository.setResult(ResultGameEntity(
            resultGame.idLesson,
            resultGame.time,
            resultGame.correctCount,
            resultGame.wrongCount
        ))
    }

    override fun getWords(idLesson: Long): Flow<Container<List<WordEntity>>> {
        return gameDataRepository.getWords(idLesson).mapLatest {
            it.map {list ->
                list.map {dataEntity ->
                    WordEntity(
                        dataEntity.eng,
                        dataEntity.russ,
                        dataEntity.idLesson
                    )
                }
            }
        }
    }
}