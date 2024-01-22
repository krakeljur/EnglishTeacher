package com.example.catalog.domain.entities

data class LessonData(
    val name: String,
    val description: String,
    val id: Long,
    val words: List<WordData>
)