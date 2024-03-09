package com.example.data.catalog.entities.api

import com.example.data.catalog.entities.WordDataEntity

data class GetWordsResponseBody(
    val words: List<WordDataEntity>
)
