package com.example.catalog.presentation.card

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalog.domain.GetLessonUseCase
import com.example.catalog.domain.entities.LessonData
import com.example.catalog.presentation.CatalogRouter
import com.example.common.Container
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val catalogRouter: CatalogRouter,
    private val getLessonUseCase: GetLessonUseCase
) : ViewModel() {


    private lateinit var lesson: Flow<Container<LessonData>>
    private var currentLessonId = 0L
    fun init(arguments: Bundle) {
        currentLessonId = catalogRouter.getCardArgs(arguments)
        lesson = getLessonUseCase.getLesson(currentLessonId)
    }

    val stateCard: Flow<StateCard> by lazy {
        lesson.mapLatest {
            when (it) {
                is Container.Success -> StateCard(false, false, it.data)
                is Container.Pending -> StateCard(true, false, null)
                is Container.Error -> StateCard(false, true, null)
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            StateCard(true, false, null)
        )
    }


    fun startGame() {
        catalogRouter.launchGameFromCard(currentLessonId)
    }

    fun goBack() {
        catalogRouter.launchBackFromCard()
    }


}

data class StateCard(
    val isLoading: Boolean,
    val isError: Boolean,
    val lesson: LessonData?
)