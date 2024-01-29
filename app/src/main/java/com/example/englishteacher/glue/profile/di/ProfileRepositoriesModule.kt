package com.example.englishteacher.glue.profile.di

import com.example.englishteacher.glue.profile.repositories.ProfileAdapter
import com.example.profile.domain.repositories.AuthRepository
import com.example.profile.domain.repositories.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface ProfileRepositoriesModule {

    @Binds
    @Singleton
    fun bindAuthRepository(
        profileAdapter: ProfileAdapter
    ) : AuthRepository

    @Binds
    @Singleton
    fun bindProfileRepository(
        profileAdapter: ProfileAdapter
    ) : ProfileRepository

}