package com.example.game.presentation.game

import androidx.lifecycle.ViewModel
import com.example.game.presentation.GameRouter
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class GameViewModel(
    private val gameRouter: GameRouter,

) : ViewModel() {


}