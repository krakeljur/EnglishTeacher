package com.example.data.game.etities.api

data class GetResultsRequestBody(
    val token: String,
    val limit: Int,
    val offset: Int
)
