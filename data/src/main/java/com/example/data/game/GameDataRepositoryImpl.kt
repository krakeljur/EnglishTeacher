package com.example.data.game

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.common.Const
import com.example.data.GameDataRepository
import com.example.data.game.etities.ResultGameEntity
import com.example.data.game.etities.api.PutResultRequestBody
import com.example.data.game.etities.room.ResultDbEntity
import com.example.data.game.sources.ResultRemoteMediator
import com.example.data.game.sources.api.ResultApi
import com.example.data.game.sources.dao.ResultDao
import com.example.data.settings.SettingsDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
class GameDataRepositoryImpl @Inject constructor(
    private val resultDao: ResultDao,
    private val resultApi: ResultApi,
    private val remoteMediatorFactory: ResultRemoteMediator.Factory,
    private val settingsDataSource: SettingsDataSource,
    coroutineScope: CoroutineScope
) : GameDataRepository {

    private var token = settingsDataSource.getToken()

    init {
        coroutineScope.launch {
            settingsDataSource.listenToken().collect {
                token = it
            }
        }
    }

    override suspend fun setResult(resultGame: ResultGameEntity) {

        val result = resultApi.setResult(
            PutResultRequestBody(
                token!!,
                resultGame.idLesson,
                resultGame.correctCount,
                resultGame.wrongCount,
                resultGame.time
            )
        )

        resultDao.save(
            ResultDbEntity(
                result.id,
                result.idLesson,
                result.time,
                result.countCorrect,
                result.countWrong
            )
        )
    }


    override fun getResults(): Flow<PagingData<ResultGameEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = Const.PAGE_SIZE
            ),
            pagingSourceFactory = { resultDao.getPagingSource() },
            remoteMediator = remoteMediatorFactory.create(token!!)
        ).flow
            .map { pagingData ->
                pagingData.map {
                    it.toResultGameEntity()
                }
            }
    }


}