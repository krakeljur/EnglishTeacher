package com.example.englishteacher.glue.catalog.repositories

import androidx.paging.PagingData
import androidx.paging.map
import com.example.catalog.domain.entities.LessonData
import com.example.catalog.domain.repositories.LessonRepository
import com.example.data.CatalogDataRepository
import com.example.data.catalog.entities.LessonDataEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class LessonAdapter @Inject constructor(
    private val catalogDataRepository: CatalogDataRepository
) : LessonRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getCatalog(): Flow<PagingData<LessonData>> {
        return catalogDataRepository.getCatalog().mapLatest { pagingData ->
            pagingData.map {
                LessonData(
                    it.name,
                    it.description,
                    it.id,
                    it.idCreator
                )
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFavorite(): Flow<PagingData<LessonData>> {
        return catalogDataRepository.getFavorite().mapLatest { pagingData ->
            pagingData.map {
                LessonData(
                    it.name,
                    it.description,
                    it.id,
                    it.idCreator
                )
            }
        }
    }

    override suspend fun addFavorite(lesson: LessonData) {
        val lessonDataEntity = LessonDataEntity(
            lesson.name,
            lesson.description,
            lesson.id,
            lesson.idCreator
        )
        catalogDataRepository.addFavorite(lessonDataEntity)
    }

    override suspend fun deleteFavorite(lesson: LessonData) {
        val lessonDataEntity = LessonDataEntity(
            lesson.name,
            lesson.description,
            lesson.id,
            lesson.idCreator
        )
        catalogDataRepository.deleteFavorite(lessonDataEntity)
    }
}