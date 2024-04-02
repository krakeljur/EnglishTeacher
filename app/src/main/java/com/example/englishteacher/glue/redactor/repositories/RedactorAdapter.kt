package com.example.englishteacher.glue.redactor.repositories

import androidx.paging.PagingData
import androidx.paging.map
import com.example.common.Container
import com.example.data.CatalogDataRepository
import com.example.data.GameDataRepository
import com.example.data.LessonDataRepository
import com.example.data.catalog.entities.WordDataEntity
import com.example.redactor.domain.entities.ResultEntity
import com.example.redactor.domain.entities.WordEntity
import com.example.redactor.domain.repositories.RedactorRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class RedactorAdapter @Inject constructor(
    private val lessonDataRepository: LessonDataRepository,
    private val catalogDataRepository: CatalogDataRepository,
    private val gameDataRepository: GameDataRepository
) : RedactorRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getWords(): Flow<Container<List<WordEntity>>> =
        catalogDataRepository.getWords().mapLatest { container ->
            container.map { list ->
                list.map {
                    WordEntity(
                        it.russ, it.eng
                    )
                }
            }
        }

    override suspend fun addWord(rus: String, eng: String, idLesson: String) {
        lessonDataRepository.addWord(WordDataEntity(rus, eng), idLesson)
    }

    override suspend fun deleteWord(wordEntity: WordEntity, idLesson: String) {
        lessonDataRepository.deleteWord(WordDataEntity(wordEntity.rus, wordEntity.eng), idLesson)
    }

    override suspend fun updateWords(idLesson: String) {
        catalogDataRepository.updateWords(idLesson)
    }

    override suspend fun patchLesson(newName: String, newDescription: String, idLesson: String) {
        lessonDataRepository.patchLesson(newName, newDescription, idLesson)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getStatistic(idLesson: String): Flow<PagingData<ResultEntity>> =
        gameDataRepository.getResults(idLesson).mapLatest { pagingData ->
            pagingData.map {
                ResultEntity(
                    it.nameUser, it.time, it.countCorrect, it.countWrong
                )
            }
        }
}