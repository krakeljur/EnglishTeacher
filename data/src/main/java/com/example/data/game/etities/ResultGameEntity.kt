package com.example.data.game.etities

import com.example.data.game.etities.room.ResultDbEntity

data class ResultGameEntity(
    val id: String,
    val idLesson: String,
    val time: Long,
    val correctCount: Int,
    val wrongCount: Int
) {
    fun toResultDbEntity(): ResultDbEntity = ResultDbEntity(
        id, idLesson, time, correctCount, wrongCount
    )
}