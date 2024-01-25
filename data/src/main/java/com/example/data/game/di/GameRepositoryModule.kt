package com.example.data.game.di

import com.example.data.GameDataRepository
import com.example.data.game.GameDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface GameRepositoryModule {


    @Binds
    @Singleton
    fun bindGameRepository(
        gameDataRepositoryImpl: GameDataRepositoryImpl
    ) : GameDataRepository
}