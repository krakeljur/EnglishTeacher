package com.example.data.catalog.sources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.data.catalog.entities.room.LessonDbEntity
import com.example.data.catalog.sources.dao.LessonDao
import dagger.assisted.AssistedFactory
import javax.inject.Inject


@ExperimentalPagingApi
class CatalogRemoteMediator @Inject constructor(
    private val lessonDao: LessonDao,
    private val
    private val token: String
) : RemoteMediator<Int, LessonDbEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LessonDbEntity>
    ): MediatorResult {
        TODO("Not yet implemented")
    }

    @AssistedFactory
    interface Factory {
        fun create(): CatalogRemoteMediator
    }
}