package com.example.data.catalog.sources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.data.catalog.entities.api.GetCatalogRequestBody
import com.example.data.catalog.entities.api.GetFavoriteRequestBody
import com.example.data.catalog.entities.api.GetLessonsFromUserIdRequestBody
import com.example.data.catalog.entities.room.LessonDbEntity
import com.example.data.catalog.sources.api.CatalogApi
import com.example.data.catalog.sources.dao.LessonDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject


@ExperimentalPagingApi
class CatalogRemoteMediator @AssistedInject constructor(
    private val lessonDao: LessonDao,
    private val catalogApi: CatalogApi,
    @Assisted("token") private val token: String,
    @Assisted("isFavorite") private val isFavorite: Boolean,
    @Assisted("idCreator") private val idCreator: String?
) : RemoteMediator<Int, LessonDbEntity>() {

    private var pageIndex = 0
    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, LessonDbEntity>
    ): MediatorResult {

        pageIndex =
            getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)

        val limit = state.config.pageSize
        val offset = pageIndex * limit

        return try {
            val lessons = fetchLessons(limit, offset)

            if (loadType == LoadType.REFRESH) lessonDao.refresh(lessons)
            else lessonDao.save(lessons)

            MediatorResult.Success(
                endOfPaginationReached = lessons.size < limit
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun fetchLessons(limit: Int, offset: Int): List<LessonDbEntity> =
        if (idCreator != null) {
            catalogApi.getLessonFromUserId(
                GetLessonsFromUserIdRequestBody(
                    idCreator, limit, offset
                )
            ).lessons.map { it.toLessonDBEntity() }
        } else if (!isFavorite) catalogApi.getCatalog(
            GetCatalogRequestBody(
                token, limit, offset
            )
        ).lessons.map {
            it.toLessonDBEntity()
        }
        else catalogApi.getFavoritesLesson(
            GetFavoriteRequestBody(
                token, limit, offset
            )
        ).favorites.map { it.toLessonDBEntity() }


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
            @Assisted("token") token: String,
            @Assisted("isFavorite") isFavorite: Boolean = false,
            @Assisted("idCreator") idCreator: String? = null
        ): CatalogRemoteMediator
    }
}