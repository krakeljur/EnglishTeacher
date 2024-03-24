package com.example.profile.domain

import androidx.paging.PagingData
import com.example.profile.domain.entities.Lesson
import com.example.profile.domain.repositories.LessonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLessonsFromUserUseCase @Inject constructor(
    private val lessonRepository: LessonRepository
) {

    fun getMyLessons(): Flow<PagingData<Lesson>> = lessonRepository.getMyLessons()
}