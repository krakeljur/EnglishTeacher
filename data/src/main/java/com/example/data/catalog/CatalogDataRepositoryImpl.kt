package com.example.data.catalog

import com.example.common.Container
import com.example.data.CatalogDataRepository
import com.example.data.catalog.entities.LessonDataEntity
import com.example.data.catalog.entities.WordDataEntity
import com.example.data.catalog.sources.CatalogDataSource
import com.example.data.settings.SettingsDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatalogDataRepositoryImpl @Inject constructor(
    private val settingsDataSource: SettingsDataSource,
    private val catalogDataSource: CatalogDataSource
) : CatalogDataRepository {
    override fun getCatalog(): Flow<Container<List<LessonDataEntity>>> {
        return catalogDataSource.getCatalog()
    }

    override fun getFavorite(): Flow<Container<List<LessonDataEntity>>> {
        return getFavorite()
    }

    override suspend fun addFavorite(idLesson: Long) {
        catalogDataSource.addFavorite(idLesson)
    }

    override suspend fun deleteFavorite(idLesson: Long) {
        catalogDataSource.deleteFavorite(idLesson)
    }

    override fun getWords(idLesson: Long): Flow<Container<List<WordDataEntity>>> {
        return catalogDataSource.getWords(idLesson)
    }


}