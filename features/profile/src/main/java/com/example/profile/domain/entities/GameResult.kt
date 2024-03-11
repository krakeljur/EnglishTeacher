package com.example.profile.domain.entities

data class GameResult(
    val id: String,
    val idLesson: String,
    val time: Long,
    val countCorrect: Int,
    val countWrong: Int
)