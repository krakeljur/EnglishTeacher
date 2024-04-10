package com.example.profile.presentation.profile

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.profile.domain.CreateOrDeleteLessonUseCase
import com.example.profile.domain.EditNameUseCase
import com.example.profile.domain.GetLessonsFromUserUseCase
import com.example.profile.domain.GetProfileUseCase
import com.example.profile.domain.LogoutUseCase
import com.example.profile.domain.entities.Lesson
import com.example.profile.presentation.ProfileRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val editNameUseCase: EditNameUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getLessonsFromUserUseCase: GetLessonsFromUserUseCase,
    private val createOrDeleteLessonUseCase: CreateOrDeleteLessonUseCase,
    private val router: ProfileRouter
) : ViewModel() {

    val profile = getProfileUseCase.getAccount()
    private val updateLessonFlow = MutableStateFlow(false)
    var showErrorFlow =
        MutableStateFlow(false)
        private set

    @OptIn(ExperimentalCoroutinesApi::class)
    val myLessons = updateLessonFlow.flatMapLatest {
        getLessonsFromUserUseCase.getMyLessons()
    }.cachedIn(viewModelScope)


    fun logout(activity: FragmentActivity) {
        viewModelScope.launch {
            try {
                logoutUseCase.logout()
                router.launchSignInFromProfile(activity)
            } catch (_: Exception) {
                showError()
            }
        }
    }

    fun editName(newName: String) {
        viewModelScope.launch {
            editNameUseCase.editName(newName)
        }
    }

    fun getStatistic() {
        router.launchStatisticFromProfile()
    }

    fun createLesson(name: String, description: String) {
        viewModelScope.launch {
            try {
                createOrDeleteLessonUseCase.createLesson(name, description)
            } catch (_: Exception) {
                showError()
            }
        }
    }

    fun deleteLesson(lessonId: String) {
        viewModelScope.launch {
            try {
                createOrDeleteLessonUseCase.deleteLesson(lessonId)
            } catch (_: Exception) {
                showError()
            }
        }
    }

    fun launchLessonRedactor(lesson: Lesson) {
        router.launchLessonRedactorFromProfile(lesson)
    }

    fun updateFlow() {
        updateLessonFlow.value = !updateLessonFlow.value
    }

    private fun showError() {
        showErrorFlow.value = !showErrorFlow.value
    }

}