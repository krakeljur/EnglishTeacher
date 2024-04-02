package com.example.data.game.etities.api

data class GetResultFromLessonRequestBody(
    val idLesson: String,
    val limit: Int,
    val offset: Int
)
