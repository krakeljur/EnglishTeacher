package com.example.data.catalog

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.RemoteMediator
import com.example.data.CatalogDataRepository
import com.example.data.catalog.entities.LessonDataEntity
import com.example.data.catalog.entities.WordDataEntity
import com.example.data.catalog.sources.CatalogNetworkDataSource
import com.example.data.catalog.sources.CatalogRemoteMediator
import com.example.data.catalog.sources.dao.LessonDao
import com.example.data.settings.SettingsDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
class CatalogDataRepositoryImpl @Inject constructor(
    private val settingsDataSource: SettingsDataSource,
    private val catalogNetworkDataSource: CatalogNetworkDataSource,
    private val lessonDao: LessonDao,
    private val remoteMediatorFactory: CatalogRemoteMediator.Factory,
    coroutineScope: CoroutineScope
) : CatalogDataRepository {

    private var token: String? = settingsDataSource.getToken()

    init {
        coroutineScope.launch {
            settingsDataSource.listenToken().collect {
                token = it
            }
        }
    }

    override fun getCatalog(): Flow<PagingData<LessonDataEntity>> {
        TODO("Not yet implemented")
    }

    override fun getFavorite(): Flow<PagingData<LessonDataEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun addFavorite(idLesson: String) {
        catalogNetworkDataSource.addFavorite(idLesson, token!!)
    }

    override suspend fun deleteFavorite(idLesson: String) {
        catalogNetworkDataSource.deleteFavorite(idLesson, token!!)
    }

    override fun getWords(idLesson: Long): Flow<PagingData<WordDataEntity>> {
        TODO("Not yet implemented")
    }


}