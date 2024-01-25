package com.example.data.game

import com.example.common.Container
import com.example.data.GameDataRepository
import com.example.data.catalog.entities.WordDataEntity
import com.example.data.game.etities.ResultGameEntity
import com.example.data.game.sources.GameDataSource
import com.example.data.settings.SettingsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class GameDataRepositoryImpl @Inject constructor(
    private val gameDataSource: GameDataSource,
    private val settingsDataSource: SettingsDataSource
) : GameDataRepository {

    private val wordsFlow: MutableStateFlow<Container<List<WordDataEntity>>> =
        MutableStateFlow(Container.Pending)

    override suspend fun setResult(resultGame: ResultGameEntity) {
        gameDataSource.setResultGame(resultGame)
    }

    override fun getWords(idLesson: Long): Flow<Container<List<WordDataEntity>>> {
        wordsFlow.value = Container.Success(gameDataSource.getWords(idLesson))
        return wordsFlow.asStateFlow()
    }


}