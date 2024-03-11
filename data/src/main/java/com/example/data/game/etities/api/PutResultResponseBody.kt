package com.example.data.game.etities.api

data class PutResultResponseBody(
    val id: String,
    val idLesson: String,
    val time : Long,
    val countCorrect: Int,
    val countWrong: Int
)
