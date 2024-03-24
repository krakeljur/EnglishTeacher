package com.example.data.lesson.di

import com.example.data.LessonDataRepository
import com.example.data.lesson.LessonDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface LessonRepositoriesModule {


    @Binds
    @Singleton
    fun bindLessonRepository(redactorDataRepository: LessonDataRepositoryImpl): LessonDataRepository

}