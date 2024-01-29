package com.example.englishteacher.glue.game.di

import com.example.englishteacher.glue.game.repositories.GameAdapter
import com.example.game.domain.repositories.GameRepository
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
        gameAdapter: GameAdapter
    ) : GameRepository
}