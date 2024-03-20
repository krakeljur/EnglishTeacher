package com.example.data.lesson.entites.api

data class CreateLessonRequestBody(
    val token: String,
    val name: String,
    val description: String
)
