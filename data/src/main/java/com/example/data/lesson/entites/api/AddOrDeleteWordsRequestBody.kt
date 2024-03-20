package com.example.data.lesson.entites.api

import com.example.data.catalog.entities.WordDataEntity

data class AddOrDeleteWordsRequestBody(
    val token: String,
    val words: List<WordDataEntity>,
    val idLesson: String
)
