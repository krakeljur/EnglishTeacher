package com.example.catalog.presentation.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalog.domain.GetLessonUseCase
import com.example.catalog.domain.entities.LessonData
import com.example.catalog.presentation.CatalogRouter
import com.example.common.Container
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val catalogRouter: CatalogRouter,
    private val getLessonUseCase: GetLessonUseCase
) : ViewModel() {


    private var lesson: MutableStateFlow<Container<LessonData>> = MutableStateFlow(Container.Pending)

    fun init(id: Long) {
        lesson = getLessonUseCase.getLesson(id) as MutableStateFlow<Container<LessonData>>
    }

    val stateCard: Flow<StateCard> = lesson.mapLatest {
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


    fun startGame() {
        catalogRouter.launchGame()
    }

    fun goBack() {
        catalogRouter.launchBack()
    }


}

data class StateCard(
    val isLoading: Boolean,
    val isError: Boolean,
    val lesson: LessonData?
)