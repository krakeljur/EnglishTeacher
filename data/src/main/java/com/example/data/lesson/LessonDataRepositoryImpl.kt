package com.example.data.lesson

import com.example.data.LessonDataRepository
import com.example.data.catalog.entities.LessonDataEntity
import com.example.data.catalog.entities.WordDataEntity
import com.example.data.catalog.sources.dao.LessonDao
import com.example.data.lesson.entites.api.AddOrDeleteWordsRequestBody
import com.example.data.lesson.entites.api.CreateLessonRequestBody
import com.example.data.lesson.entites.api.DeleteLessonRequestBody
import com.example.data.lesson.entites.api.PatchLessonRequestBody
import com.example.data.lesson.sources.api.RedactorApi
import com.example.data.settings.SettingsDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class LessonDataRepositoryImpl @Inject constructor(
    private val redactorApi: RedactorApi,
    private val lessonDao: LessonDao,
    private val settings: SettingsDataSource,
    coroutineScope: CoroutineScope
) : LessonDataRepository {

    private var token = settings.getToken()
    private var account = settings.getAccount()

    init {
        coroutineScope.launch {
            settings.listenAccount().collectLatest {
                account = it
            }
            settings.listenToken().collectLatest {
                token = it
            }
        }
    }

    override suspend fun createLesson(name: String, description: String) {
        redactorApi.createLesson(CreateLessonRequestBody(token!!, name, description))
        lessonDao.clear()
    }

    override suspend fun addWord(word: WordDataEntity, idLesson: String) {
        redactorApi.addWords(AddOrDeleteWordsRequestBody(token!!, listOf(word), idLesson))
    }

    override suspend fun deleteWord(word: WordDataEntity, idLesson: String) {
        redactorApi.deleteWords(AddOrDeleteWordsRequestBody(token!!, listOf(word), idLesson))
    }

    override suspend fun deleteLesson(idLesson: String) {
        redactorApi.deleteLesson(DeleteLessonRequestBody(token!!, idLesson))
        lessonDao.clear()
    }

    override suspend fun patchLesson(patchedLesson: LessonDataEntity) {
        redactorApi.patchLesson(
            PatchLessonRequestBody(
                token!!,
                patchedLesson.id,
                patchedLesson.name,
                patchedLesson.description
            )
        )
        lessonDao.save(patchedLesson.toLessonDBEntity())
    }

}