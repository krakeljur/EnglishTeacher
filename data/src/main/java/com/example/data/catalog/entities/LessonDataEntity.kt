package com.example.data.catalog.entities

import com.example.data.catalog.entities.room.LessonDbEntity

data class LessonDataEntity(
    val name: String,
    val description: String,
    val id: String,
    val idCreator: String,
    val isFavorite: Boolean,
    val countWord: Int?
) {
    fun toLessonDBEntity(newFavoriteStatus: Boolean? = null, newCountWord: Int? = null) =
        LessonDbEntity(
            id,
            name,
            description,
            idCreator,
            newFavoriteStatus ?: isFavorite,
            newCountWord ?: countWord
        )

}