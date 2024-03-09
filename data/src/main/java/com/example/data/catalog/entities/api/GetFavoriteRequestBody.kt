package com.example.data.catalog.entities.api

data class GetFavoriteRequestBody(
    val token: String,
    val limit: Int,
    val offset: Int
)
