package com.example.data.lesson.entites.api

data class PatchLessonRequestBody(
    val token: String,
    val idLesson: String,
    val name: String,
    val description: String
)
