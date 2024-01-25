package com.example.data.game.di

import com.example.data.game.sources.GameDataSource
import com.example.data.game.sources.InMemoryGameDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface GameSourceModule {


    @Binds
    @Singleton
    fun bindGameSource(
        inMemoryGameDataSource: InMemoryGameDataSource
    ): GameDataSource
}