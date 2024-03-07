package com.example.data.catalog.entities.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.data.catalog.entities.WordDataEntity


@Entity(
    tableName = "word",
    foreignKeys = [ForeignKey(
        entity = LessonDbEntity::class,
        parentColumns = ["id"],
        childColumns = ["lesson_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class WordDbEntity(
    @PrimaryKey(autoGenerate = true) val id: String,
    @ColumnInfo(name = "lesson_id") val idLesson: String,
    val rus: String,
    val eng: String
) {

    fun toWordDataEntity() : WordDataEntity = WordDataEntity(rus, eng, idLesson)
}
