package com.example.englishteacher.glue.signUp.di

import com.example.englishteacher.glue.signUp.repositories.SignUpAdapter
import com.example.sign_up.domain.repositories.SignUpRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface SignUpRepositoryModule {


    @Binds
    @Singleton
    fun bindSignUpRepository(
        signUpAdapter: SignUpAdapter
    ) : SignUpRepository

}