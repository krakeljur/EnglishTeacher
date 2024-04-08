package com.example.redactor.presentation.redactor

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.common.Container
import com.example.redactor.domain.GetStatisticUseCase
import com.example.redactor.domain.GetWordsUseCase
import com.example.redactor.domain.PatchLessonUseCase
import com.example.redactor.domain.RedactWordUseCase
import com.example.redactor.domain.entities.LessonEntity
import com.example.redactor.domain.entities.WordEntity
import com.example.redactor.presentation.LessonRedactorRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LessonRedactorViewModel @Inject constructor(
    private val getStatisticUseCase: GetStatisticUseCase,
    private val getWordsUseCase: GetWordsUseCase,
    private val patchLessonUseCase: PatchLessonUseCase,
    private val redactWordUseCase: RedactWordUseCase,
    private val router: LessonRedactorRouter,
) : ViewModel() {

    private val lessonFlow: MutableStateFlow<Container<LessonEntity>> =
        MutableStateFlow(Container.Pending)
    private val wordsFlow = getWordsUseCase.getWords()
    private val isGradeFlow = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    val statisticFlow = lessonFlow.flatMapLatest { container ->
        when (container) {
            is Container.Success -> {
                getStatisticUseCase.getStatistic(container.data.id)
            }

            else -> {
                MutableStateFlow(PagingData.empty())
            }
        }
    }.cachedIn(viewModelScope)

    val stateRedactor: Flow<StateRedactor> = combine(wordsFlow, lessonFlow) { words, lesson ->
        return@combine StateRedactor(
            isError = (words is Container.Error || lesson is Container.Error),
            isLoading = (words is Container.Pending || lesson is Container.Pending),
            lesson = if (lesson is Container.Success) lesson.data else null,
            words = if (words is Container.Success) words.data else emptyList()
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        StateRedactor(
            isError = false,
            isLoading = true,
            lesson = null,
            words = emptyList()
        )
    )

    fun init(args: Bundle) {
        val lesson = router.getRedactorArgs(args)
        lessonFlow.value = Container.Success(lesson)
        viewModelScope.launch {
            getWordsUseCase.updateWords(lesson.id)
        }
    }

    fun patchLesson(newName: String, newDescription: String) {
        viewModelScope.launch {
            val lessonId = (lessonFlow.value as Container.Success).data.id
            lessonFlow.value = Container.Pending
            patchLessonUseCase.patchLesson(newName, newDescription, lessonId)
            lessonFlow.value = Container.Success(LessonEntity(lessonId, newName, newDescription))
        }
    }

    fun addWord(rus: String, eng: String) {
        viewModelScope.launch {
            val lessonId = (lessonFlow.value as Container.Success).data.id
            redactWordUseCase.addWord(rus, eng, lessonId)
            getWordsUseCase.updateWords(lessonId)
        }
    }

    fun deleteWord(wordEntity: WordEntity) {
        viewModelScope.launch {
            val lessonId = (lessonFlow.value as Container.Success).data.id
            redactWordUseCase.deleteWord(wordEntity, lessonId)
            getWordsUseCase.updateWords(lessonId)
        }
    }

    fun getCurrentGradeStatus() = isGradeFlow.value

    fun changeGradeStatus(new: Boolean) {
        isGradeFlow.value = new
    }

    fun getGradeFlow() = isGradeFlow.asStateFlow()
}

data class StateRedactor(
    val isError: Boolean,
    val isLoading: Boolean,
    val lesson: LessonEntity?,
    val words: List<WordEntity>
)