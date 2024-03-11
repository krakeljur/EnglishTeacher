package com.example.data.game.etities.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.game.etities.ResultGameEntity


@Entity(tableName = "result")
data class ResultDbEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "id_lesson") val idLesson: String,
    val time: Long,
    @ColumnInfo(name = "count_correct") val correctCount: Int,
    @ColumnInfo(name = "count_wrong") val wrongCount: Int
) {
    fun toResultGameEntity() : ResultGameEntity = ResultGameEntity(
        id, idLesson, time, correctCount, wrongCount
    )
}
