package com.example.game.domain.entities

data class ResultGame(
    val idLesson: Long,
    val correctCount: Int,
    val wrongCount: Int,
    val time: Long
)