package com.example.data.catalog.entities.api

import com.example.data.catalog.entities.LessonDataEntity

data class GetFavoriteResponseBody(
    val favorites: List<LessonDataEntity>
)
