package com.example.data.game.etities.api

data class PutResultRequestBody(
    val token: String,
    val idLesson: String,
    val countCorrect: Int,
    val countWrong: Int,
    val time: Long
)
