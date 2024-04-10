package com.example.catalog.presentation.card

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalog.domain.GetWordsUseCase
import com.example.catalog.domain.entities.LessonData
import com.example.catalog.domain.entities.WordData
import com.example.catalog.presentation.CatalogRouter
import com.example.common.Container
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val catalogRouter: CatalogRouter,
    private val getWordsUseCase: GetWordsUseCase
) : ViewModel() {


    private val lesson: MutableStateFlow<Container<LessonData>> =
        MutableStateFlow(Container.Pending)
    private val words = getWordsUseCase.getWords()
    fun init(arguments: Bundle) {
        val lessonData = catalogRouter.getCardArgs(arguments)
        lesson.value = Container.Success(lessonData)

        viewModelScope.launch {
            getWordsUseCase.updateWords(lessonData.id)
        }
    }

    val stateCard: Flow<StateCard> by lazy {
        combine(lesson, words) { lesson, words ->
            return@combine StateCard(
                isLoading = lesson is Container.Pending || words is Container.Pending,
                isError = lesson is Container.Error || words is Container.Error,
                lesson = if (lesson is Container.Success) lesson.data else null,
                words = if (words is Container.Success) words.data else emptyList()
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            StateCard(
                isLoading = true,
                isError = false,
                lesson = null,
                words = emptyList()
            )
        )
    }


    fun startGame() {
        catalogRouter.launchGameFromCard((lesson.value as Container.Success).data.id)
    }


}

data class StateCard(
    val isLoading: Boolean,
    val isError: Boolean,
    val lesson: LessonData?,
    val words: List<WordData>
)