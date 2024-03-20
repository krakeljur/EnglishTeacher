package com.example.data.catalog.entities.api

data class GetLessonsFromUserIdRequestBody(
    val idCreator: String,
    val limit: Int,
    val offset: Int
)
