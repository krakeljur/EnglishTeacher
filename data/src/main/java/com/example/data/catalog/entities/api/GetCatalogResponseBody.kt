package com.example.data.catalog.entities.api

import com.example.data.catalog.entities.LessonDataEntity

data class GetCatalogResponseBody(
    val lessons: List<LessonDataEntity>
)
