package com.example.profile.domain.entities

data class GameResult(
    val idLesson: Long,
    val time: Long,
    val countCorrect: Int,
    val countWrong: Int
)