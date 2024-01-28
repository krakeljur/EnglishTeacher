package com.example.englishteacher.navigation.di

import com.example.englishteacher.navigation.Router
import com.example.englishteacher.navigation.RouterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton


@Module
@InstallIn(ActivityComponent::class)
interface RouterModule {


    @Binds
    @Singleton
    fun bindRouter(
        routerImpl: RouterImpl
    ) : Router
}