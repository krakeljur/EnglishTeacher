package com.example.data.catalog.entities.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.catalog.entities.LessonDataEntity


@Entity(tableName = "lesson")
data class LessonDbEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    @ColumnInfo(name = "creator_id") val idCreator: String,
    @ColumnInfo(name = "is_favorite") val isFavorite : Boolean
) {

    fun toLessonDataEntity(): LessonDataEntity = LessonDataEntity(
        name,
        description,
        id,
        idCreator,
        isFavorite
    )
}
