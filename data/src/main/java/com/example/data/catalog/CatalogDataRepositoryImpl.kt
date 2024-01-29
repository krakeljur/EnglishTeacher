package com.example.data.catalog

import com.example.common.Container
import com.example.data.CatalogDataRepository
import com.example.data.catalog.entities.LessonDataEntity
import com.example.data.catalog.entities.WordDataEntity
import com.example.data.catalog.sources.CatalogDataSource
import com.example.data.settings.SettingsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class CatalogDataRepositoryImpl @Inject constructor(
    private val settingsDataSource: SettingsDataSource,
    private val catalogDataSource: CatalogDataSource
) : CatalogDataRepository {

    private val catalog: MutableStateFlow<Container<List<LessonDataEntity>>> =
        MutableStateFlow(Container.Success(emptyList()))

    private val favorites: MutableStateFlow<Container<List<LessonDataEntity>>> =
        MutableStateFlow(Container.Success(emptyList()))

    private val words: MutableStateFlow<Container<List<WordDataEntity>>> =
        MutableStateFlow(Container.Success(emptyList()))

    override fun getCatalog(): Flow<Container<List<LessonDataEntity>>> {
        catalog.value = Container.Success(catalogDataSource.getCatalog())
        return catalog.asStateFlow()
    }

    override fun getFavorite(): Flow<Container<List<LessonDataEntity>>> {
        favorites.value = Container.Success(catalogDataSource.getFavorite())
        return favorites
    }

    override suspend fun addFavorite(idLesson: Long) {
        catalogDataSource.addFavorite(idLesson)
    }

    override suspend fun deleteFavorite(idLesson: Long) {
        catalogDataSource.deleteFavorite(idLesson)
    }

    override fun getWords(idLesson: Long): Flow<Container<List<WordDataEntity>>> {
        words.value = Container.Success(catalogDataSource.getWords(idLesson))
        return words
    }

    override suspend fun updateCatalog() {
        favorites.value = Container.Success(catalogDataSource.getCatalog())
    }

    override suspend fun updateFavorite() {
        favorites.value = Container.Success(catalogDataSource.getFavorite())
    }

    override suspend fun updateWords(idLesson: Long) {
        words.value = Container.Success(catalogDataSource.getWords(idLesson))
    }

    override fun getLesson(idLesson: Long): Flow<Container<LessonDataEntity>> {
        return MutableStateFlow(Container.Success(catalogDataSource.getLesson(idLesson))).asStateFlow()
    }


}