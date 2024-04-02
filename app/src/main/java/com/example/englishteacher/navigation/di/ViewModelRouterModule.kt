package com.example.englishteacher.navigation.di

import com.example.catalog.presentation.CatalogRouter
import com.example.englishteacher.navigation.Router
import com.example.game.presentation.GameRouter
import com.example.profile.presentation.ProfileRouter
import com.example.redactor.presentation.LessonRedactorRouter
import com.example.sign_in.presentation.SignInRouter
import com.example.sign_up.presentation.SignUpRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface ViewModelRouterModule {


    @Binds
    fun bindGame(router: Router): GameRouter

    @Binds
    fun bindCatalog(router: Router): CatalogRouter

    @Binds
    fun bindProfile(router: Router): ProfileRouter

    @Binds
    fun bindSignUp(router: Router): SignUpRouter

    @Binds
    fun bindSignIn(router: Router): SignInRouter

    @Binds
    fun bindRedactor(router: Router): LessonRedactorRouter
}