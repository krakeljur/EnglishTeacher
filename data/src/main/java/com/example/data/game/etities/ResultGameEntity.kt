package com.example.data.game.etities

import com.example.data.game.etities.room.ResultDbEntity

data class ResultGameEntity(
    val id: String,
    val idLesson: String,
    val time: Long,
    val countCorrect: Int,
    val countWrong: Int
) {
    fun toResultDbEntity(): ResultDbEntity = ResultDbEntity(
        id, idLesson, time, countCorrect, countWrong
    )
}