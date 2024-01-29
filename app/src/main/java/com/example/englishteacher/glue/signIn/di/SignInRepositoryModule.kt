package com.example.englishteacher.glue.signIn.di

import com.example.englishteacher.glue.signIn.repositories.SignInAdapter
import com.example.sign_in.domain.repositories.SignInRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface SignInRepositoryModule {


    @Binds
    @Singleton
    fun bindSignInRepository(
        signInAdapter: SignInAdapter
    ) : SignInRepository
}