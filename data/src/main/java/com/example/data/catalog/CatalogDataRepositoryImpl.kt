package com.example.data.catalog

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.common.Const
import com.example.common.Container
import com.example.data.CatalogDataRepository
import com.example.data.catalog.entities.LessonDataEntity
import com.example.data.catalog.entities.WordDataEntity
import com.example.data.catalog.entities.api.AddOrDeleteFavoriteRequestBody
import com.example.data.catalog.entities.api.GetWordsRequestBody
import com.example.data.catalog.sources.CatalogRemoteMediator
import com.example.data.catalog.sources.api.CatalogApi
import com.example.data.catalog.sources.dao.LessonDao
import com.example.data.settings.SettingsDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
class CatalogDataRepositoryImpl @Inject constructor(
    private val settingsDataSource: SettingsDataSource,
    private val catalogApi: CatalogApi,
    private val lessonDao: LessonDao,
    private val remoteMediatorFactory: CatalogRemoteMediator.Factory,
    coroutineScope: CoroutineScope
) : CatalogDataRepository {

    private var token: String? = settingsDataSource.getToken()

    private val wordsFlow: MutableStateFlow<Container<List<WordDataEntity>>> =
        MutableStateFlow(Container.Pending)

    init {
        coroutineScope.launch {
            settingsDataSource.listenToken().collect {
                token = it
            }
        }
    }

    override fun getCatalog(isFavorite: Boolean, searchBy: String): Flow<PagingData<LessonDataEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = Const.PAGE_SIZE,
                initialLoadSize = Const.PAGE_SIZE
            ),
            remoteMediator = remoteMediatorFactory.create(token!!, isFavorite),
            pagingSourceFactory = { lessonDao.getPagingSourceCatalog(isFavorite) }
        ).flow
            .map { pagingData ->
                pagingData.map { lessonDbEntity ->
                    lessonDbEntity.toLessonDataEntity()
                }
            }
    }


    override suspend fun addFavorite(lesson: LessonDataEntity) {
        catalogApi.addFavorite(AddOrDeleteFavoriteRequestBody(token!!, lesson.id))
        lessonDao.save(lesson.toLessonDBEntity())
    }

    override suspend fun deleteFavorite(lesson: LessonDataEntity) {
        catalogApi.deleteFavorite(AddOrDeleteFavoriteRequestBody(token!!, lesson.id))
        lessonDao.save(lesson.toLessonDBEntity())
    }

    override fun getWords(): Flow<Container<List<WordDataEntity>>> = wordsFlow.asStateFlow()
    override suspend fun updateWords(idLesson: String) {
        val words = catalogApi.getWords(GetWordsRequestBody(idLesson)).words

        wordsFlow.value = Container.Success(words)
    }


}