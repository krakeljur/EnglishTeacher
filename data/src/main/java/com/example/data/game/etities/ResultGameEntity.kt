package com.example.data.game.etities

data class ResultGameEntity(
    val idLesson: Long,
    val time: Long,
    val correctCount: Int,
    val wrongCount: Int
)