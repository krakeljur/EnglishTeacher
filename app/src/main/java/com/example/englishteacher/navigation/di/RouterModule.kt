package com.example.englishteacher.navigation.di

import com.example.englishteacher.navigation.Router
import com.example.englishteacher.navigation.RouterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RouterModule {


    @Binds
    @Singleton
    fun bindRouter(
        routerImpl: RouterImpl
    ) : Router
}