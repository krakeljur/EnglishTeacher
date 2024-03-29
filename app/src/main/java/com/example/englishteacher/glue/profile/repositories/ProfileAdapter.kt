package com.example.englishteacher.glue.profile.repositories

import androidx.paging.PagingData
import androidx.paging.map
import com.example.common.Container
import com.example.data.AccountsDataRepository
import com.example.data.CatalogDataRepository
import com.example.data.GameDataRepository
import com.example.data.LessonDataRepository
import com.example.profile.domain.entities.GameResult
import com.example.profile.domain.entities.Lesson
import com.example.profile.domain.entities.Profile
import com.example.profile.domain.repositories.AuthRepository
import com.example.profile.domain.repositories.LessonRepository
import com.example.profile.domain.repositories.ProfileRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class ProfileAdapter @Inject constructor(
    private val accountsDataRepository: AccountsDataRepository,
    private val gameDataRepository: GameDataRepository,
    private val lessonDataRepository: LessonDataRepository,
    private val catalogDataRepository: CatalogDataRepository,
) : AuthRepository, ProfileRepository, LessonRepository {
    override suspend fun logout() {
        accountsDataRepository.logOut()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAccount(): Flow<Container<Profile>> {
        return accountsDataRepository.getAccount().mapLatest {
            it.map { acc ->
                Profile(
                    acc.id,
                    acc.name,
                    acc.login
                )
            }
        }
    }

    override suspend fun setUsername(newName: String) {
        accountsDataRepository.setAccountUsername(newName)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getStatistic(): Flow<PagingData<GameResult>> {
        return gameDataRepository.getResults().mapLatest { pagingData ->
            pagingData.map {
                GameResult(
                    it.id,
                    it.idLesson,
                    it.time,
                    it.countCorrect,
                    it.countWrong
                )
            }
        }
    }

    override suspend fun createLesson(name: String, description: String) {
        lessonDataRepository.createLesson(name, description)
    }

    override suspend fun deleteLesson(idLesson: String) {
        lessonDataRepository.deleteLesson(idLesson)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getMyLessons(): Flow<PagingData<Lesson>> =
        catalogDataRepository.getCatalog(isOnlyMy = true).mapLatest { pagingData ->
            pagingData.map {
                Lesson(
                    it.name,
                    it.description,
                    it.id,
                    it.idCreator
                )
            }
        }
}