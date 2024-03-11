package com.example.game.domain

import com.example.game.domain.repositories.GameRepository
import javax.inject.Inject

class UpdateWordsUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    suspend fun updateWords(idLesson: String) {
        gameRepository.updateWords(idLesson)
    }
}