package com.example.data.catalog.entities

import com.example.data.catalog.entities.room.LessonDbEntity

data class LessonDataEntity(
    val name: String,
    val description: String,
    val id: String,
    val idCreator: String,
    val isFavorite: Boolean
) {
    fun toLessonDBEntity() = LessonDbEntity(id, name, description, idCreator, isFavorite)

}