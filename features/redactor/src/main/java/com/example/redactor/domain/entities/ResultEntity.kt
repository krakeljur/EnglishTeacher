package com.example.redactor.domain.entities

data class ResultEntity(
    val idUser: String,
    val nameUser: String,
    val time: Long,
    val countCorrect: Int,
    val countWrong: Int
)
