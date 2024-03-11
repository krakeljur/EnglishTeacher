package com.example.game.domain.entities

data class ResultGame(
    val idLesson: String,
    val correctCount: Int,
    val wrongCount: Int,
    val time: Long
)