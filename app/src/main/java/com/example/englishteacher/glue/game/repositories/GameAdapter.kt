package com.example.englishteacher.glue.game.repositories

import com.example.common.Container
import com.example.data.CatalogDataRepository
import com.example.data.GameDataRepository
import com.example.data.game.etities.ResultGameEntity
import com.example.game.domain.entities.ResultGame
import com.example.game.domain.entities.WordEntity
import com.example.game.domain.repositories.GameRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class GameAdapter @Inject constructor(
    private val gameDataRepository: GameDataRepository,
    private val catalogDataRepository: CatalogDataRepository
) : GameRepository {
    override suspend fun setResult(resultGame: ResultGame) {
        gameDataRepository.setResult(
            ResultGameEntity(
                "", "",
                resultGame.idLesson,
                resultGame.time,
                resultGame.correctCount,
                resultGame.wrongCount
            )
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getWords(): Flow<Container<List<WordEntity>>> =
        catalogDataRepository.getWords().mapLatest { container ->
            container.map { list ->
                list.map {
                    WordEntity(
                        it.eng,
                        it.russ
                    )
                }
            }
        }

    override suspend fun updateWords(idLesson: String) {
        catalogDataRepository.updateWords(idLesson)
    }
}