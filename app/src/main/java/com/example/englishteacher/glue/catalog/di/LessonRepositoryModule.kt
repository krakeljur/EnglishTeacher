package com.example.englishteacher.glue.catalog.di

import com.example.catalog.domain.repositories.LessonRepository
import com.example.englishteacher.glue.catalog.repositories.LessonAdapter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface LessonRepositoryModule {

    @Binds
    @Singleton
    fun bindLessonRepository(
        lessonAdapter: LessonAdapter
    ) : LessonRepository
}