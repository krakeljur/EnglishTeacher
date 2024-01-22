package com.example.catalog.domain

import com.example.catalog.domain.entities.LessonData
import com.example.catalog.domain.repositories.LessonRepository
import com.example.common.Container
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatalogUseCase @Inject constructor(
    private val lessonRepository: LessonRepository
) {

    fun getCatalog(): Flow<Container<List<LessonData>>> {
        return lessonRepository.getCatalog()
    }
}