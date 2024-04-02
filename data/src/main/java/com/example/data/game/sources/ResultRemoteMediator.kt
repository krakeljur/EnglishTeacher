package com.example.data.game.sources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.data.game.etities.api.GetResultFromLessonRequestBody
import com.example.data.game.etities.api.GetResultsRequestBody
import com.example.data.game.etities.room.ResultDbEntity
import com.example.data.game.sources.api.ResultApi
import com.example.data.game.sources.dao.ResultDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@ExperimentalPagingApi
class ResultRemoteMediator @AssistedInject constructor(
    private val resultDao: ResultDao,
    private val resultApi: ResultApi,
    @Assisted("token") private val token: String,
    @Assisted("idLesson") private val idLesson: String,
) : RemoteMediator<Int, ResultDbEntity>() {

    private var pageIndex = 0
    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, ResultDbEntity>
    ): MediatorResult {

        pageIndex =
            getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)

        val limit = state.config.pageSize
        val offset = limit * pageIndex

        return try {
            val results = fetchResults(limit, offset)

            if (loadType == LoadType.REFRESH) resultDao.refresh(results)
            else resultDao.save(results)

            MediatorResult.Success(
                endOfPaginationReached = results.size < limit
            )

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }

    }

    private suspend fun fetchResults(limit: Int, offset: Int): List<ResultDbEntity> =
        if (idLesson.isBlank())
            resultApi.getMyResults(GetResultsRequestBody(token, limit, offset)).results.map {
                it.toResultDbEntity()
            }
        else {
            resultApi.getResultsFromLesson(
                GetResultFromLessonRequestBody(
                    idLesson,
                    limit,
                    offset
                )
            ).results.map {
                it.toResultDbEntity()
            }
        }


    private fun getPageIndex(loadType: LoadType): Int? {
        pageIndex = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return null
            LoadType.APPEND -> ++pageIndex
        }
        return pageIndex
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("token") token: String, @Assisted("idLesson") idLesson: String = ""
        ): ResultRemoteMediator
    }


}