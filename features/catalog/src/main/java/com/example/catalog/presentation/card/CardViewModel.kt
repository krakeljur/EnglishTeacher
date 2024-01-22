package com.example.catalog.presentation.card

import androidx.lifecycle.ViewModel
import com.example.catalog.domain.GetLessonUseCase
import com.example.catalog.domain.entities.LessonData
import com.example.catalog.presentation.Router
import com.example.common.Container
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val router: Router,
    private val getLessonUseCase: GetLessonUseCase
) : ViewModel() {


    fun getLesson(id: Long): Flow<Container<LessonData>> {
        return getLessonUseCase.getLesson(id)
    }

    fun startGame() {
        router.launchGame()
    }

    fun goBack() {
        router.launchBack()
    }
}