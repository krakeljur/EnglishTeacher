package com.example.data.catalog.entities.api

data class AddOrDeleteFavoriteRequestBody(
    val token: String,
    val lessonId: String
)
