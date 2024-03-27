package com.example.englishteacher.glue.catalog.repositories

import androidx.paging.PagingData
import androidx.paging.map
import com.example.catalog.domain.entities.LessonData
import com.example.catalog.domain.entities.WordData
import com.example.catalog.domain.repositories.LessonRepository
import com.example.common.Container
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
    override fun getCatalog(isFavorite: Boolean, searchBy: String): Flow<PagingData<LessonData>> {
        return catalogDataRepository.getCatalog(isFavorite, searchBy).mapLatest { pagingData ->
            pagingData.map {
                LessonData(
                    it.name,
                    it.description,
                    it.id,
                    it.idCreator,
                    it.isFavorite,
                    it.countWord
                )
            }
        }
    }


    override suspend fun addFavorite(lesson: LessonData) {
        val lessonDataEntity = LessonDataEntity(
            lesson.name,
            lesson.description,
            lesson.id,
            lesson.idCreator,
            lesson.isFavorite,
            lesson.countWord
        )
        catalogDataRepository.addFavorite(lessonDataEntity)
    }

    override suspend fun deleteFavorite(lesson: LessonData) {
        val lessonDataEntity = LessonDataEntity(
            lesson.name,
            lesson.description,
            lesson.id,
            lesson.idCreator,
            lesson.isFavorite,
            lesson.countWord
        )
        catalogDataRepository.deleteFavorite(lessonDataEntity)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getWords(): Flow<Container<List<WordData>>> = catalogDataRepository.getWords()
        .mapLatest { container ->
            container.map { list ->
                list.map {
                    WordData(
                        it.russ,
                        it.eng
                    )
                }

            }

        }

    override suspend fun updateWords(idLesson: String) {
        catalogDataRepository.updateWords(idLesson)
    }
}