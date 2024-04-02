package com.example.englishteacher.glue.redactor.di

import com.example.englishteacher.glue.redactor.repositories.RedactorAdapter
import com.example.redactor.domain.repositories.RedactorRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RedactorRepositoryModule {


    @Binds
    @Singleton
    fun bindRedactorRepository(
        repository: RedactorAdapter
    ): RedactorRepository

}