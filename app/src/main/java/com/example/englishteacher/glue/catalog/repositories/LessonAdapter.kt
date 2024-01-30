package com.example.englishteacher.glue.catalog.repositories

import com.example.catalog.domain.entities.LessonData
import com.example.catalog.domain.entities.WordData
import com.example.catalog.domain.repositories.LessonRepository
import com.example.common.Container
import com.example.data.CatalogDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LessonAdapter @Inject constructor(
    private val catalogDataRepository: CatalogDataRepository
) : LessonRepository {
    override fun getLesson(lessonId: Long): Flow<Container<LessonData>> {
        return combine(
            catalogDataRepository.getWords(lessonId),
            catalogDataRepository.getLesson(lessonId)
        ) { words, lesson ->
            return@combine (if (words is Container.Success && lesson is Container.Success) {
                lesson.map {
                    LessonData(
                        it.name,
                        it.description,
                        it.id,
                        words.data.map { WordData(it.russ, it.eng) }
                    )
                }
            } else {
                Container.Pending
            })
        }
    }

    override fun getCatalog(): Flow<Container<List<LessonData>>> {
        return catalogDataRepository.getCatalog().map {
            it.map { list ->
                list.map { dataEntity ->
                    LessonData(
                        dataEntity.name,
                        dataEntity.description,
                        dataEntity.id,
                        emptyList()
                    )
                }
            }
        }
    }

    override fun getFavorite(): Flow<Container<List<LessonData>>> {
        return catalogDataRepository.getFavorite().map {
            it.map { list ->
                list.map { dataEntity ->
                    LessonData(
                        dataEntity.name,
                        dataEntity.description,
                        dataEntity.id,
                        emptyList()
                    )
                }
            }
        }
    }

    override suspend fun addFavorite(idLesson: Long) {
        catalogDataRepository.addFavorite(idLesson)
    }

    override suspend fun deleteFavorite(idLesson: Long) {
        catalogDataRepository.deleteFavorite(idLesson)
    }

    override suspend fun update() {
        catalogDataRepository.updateCatalog()
        catalogDataRepository.updateFavorite()
    }
}