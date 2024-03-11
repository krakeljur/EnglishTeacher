package com.example.game.domain

import com.example.game.domain.entities.ResultGame
import com.example.game.domain.repositories.GameRepository
import javax.inject.Inject

class SetResultUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    suspend fun setResult(resultGame: ResultGame) {
        gameRepository.setResult(resultGame)
    }
}