package com.example.game.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Container
import com.example.game.domain.GetWordsUseCase
import com.example.game.domain.SetResultUseCase
import com.example.game.domain.entities.ResultGame
import com.example.game.domain.entities.WordEntity
import com.example.game.presentation.GameRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameRouter: GameRouter,
    private val getWordsUseCase: GetWordsUseCase,
    private val setResultUseCase: SetResultUseCase
) : ViewModel() {

    private val correctAnswers = MutableStateFlow(0)
    private val wrongAnswers = MutableStateFlow(0)
    private lateinit var words: Flow<Container<List<WordEntity>>>
    fun init(idLesson: Long) {
        words = getWordsUseCase.getWords(idLesson)
    }

    val gameStateFlow by lazy {
        combine(correctAnswers, wrongAnswers, words) { correct, wrong, words ->
            when (words) {
                is Container.Pending -> GameState(
                    word = null,
                    countWrong = 0,
                    countCorrect = 0,
                    isLoading = true,
                    isError = false
                )

                is Container.Error -> GameState(
                    word = null,
                    countWrong = 0,
                    countCorrect = 0,
                    isLoading = false,
                    isError = true
                )

                is Container.Success -> GameState(
                    word = if (correct + wrong > words.data.lastIndex) null else words.data[correct + wrong],
                    countWrong = wrong,
                    countCorrect = correct,
                    isLoading = false,
                    isError = false
                )
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            GameState(
                word = null,
                countWrong = 0,
                countCorrect = 0,
                isLoading = true,
                isError = false
            )
        )
    }

    fun setResult(resultGame: ResultGame) {
        viewModelScope.launch {
            setResultUseCase.setResult(resultGame)
        }
        clear()
        gameRouter.launchLesson()
    }

    private fun clear() {
        correctAnswers.value = 0
        wrongAnswers.value = 0
    }

    fun addCorrect() {
        correctAnswers.value += 1
    }
    fun addWrong() {
        wrongAnswers.value += 1
    }


}

data class GameState(
    val word: WordEntity?,
    val countWrong: Int,
    val countCorrect: Int,
    val isLoading: Boolean,
    val isError: Boolean
)