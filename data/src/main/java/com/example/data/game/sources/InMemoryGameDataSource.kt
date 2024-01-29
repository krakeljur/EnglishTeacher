package com.example.data.game.sources

import com.example.data.catalog.entities.WordDataEntity
import com.example.data.game.etities.ResultGameEntity
import javax.inject.Inject

class InMemoryGameDataSource @Inject constructor() : GameDataSource {

    private val words = listOf(WordDataEntity(
        "Корабль",
        "Ship",
        1
    ),
        WordDataEntity(
            "Мама",
            "Mother",
            1
        ),
        WordDataEntity(
            "Папа",
            "Father",
            1
        ),
        WordDataEntity(
            "Дверь",
            "Door",
            2
        ),
        WordDataEntity(
            "Лампа",
            "Lamp",
            2
        ),
        WordDataEntity(
            "Мяч",
            "Ball",
            2
        ),)


    private val results = mutableListOf(
        ResultGameEntity(
            idLesson = 1L,
            time = 15000L,
            correctCount = 3,
            wrongCount = 0
        ),
    )

    override suspend fun setResultGame(resultGameEntity: ResultGameEntity) {
        results.add(resultGameEntity)
    }

    override fun getWords(idLesson: Long): List<WordDataEntity> {
        return words.filter { it.idLesson == idLesson }
    }

    override fun getResults(): List<ResultGameEntity> {
        return results
    }
}