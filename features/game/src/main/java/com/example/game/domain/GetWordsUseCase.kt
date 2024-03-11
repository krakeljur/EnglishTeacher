package com.example.game.domain

import com.example.common.Container
import com.example.game.domain.entities.WordEntity
import com.example.game.domain.repositories.GameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWordsUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    fun getWords() : Flow<Container<List<WordEntity>>> {
        return gameRepository.getWords()
    }
}