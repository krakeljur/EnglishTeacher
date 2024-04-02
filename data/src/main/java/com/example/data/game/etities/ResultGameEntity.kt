package com.example.data.game.etities

import com.example.data.game.etities.room.ResultDbEntity

data class ResultGameEntity(
    val id: String,
    val nameUser: String,
    val idLesson: String,
    val time: Long,
    val countCorrect: Int,
    val countWrong: Int
) {
    fun toResultDbEntity(): ResultDbEntity = ResultDbEntity(
        id, nameUser, idLesson, time, countCorrect, countWrong
    )
}