package com.example.data.base.retrofit.di

import com.example.common.Const
import com.example.data.accounts.sources.api.AccountsApi
import com.example.data.base.retrofit.NetworkErrorInterceptor
import com.example.data.catalog.sources.api.CatalogApi
import com.example.data.game.sources.api.ResultApi
import com.example.data.lesson.sources.api.RedactorApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(Const.BASE_URL)
        .client(
            OkHttpClient().newBuilder()
                .addInterceptor(NetworkErrorInterceptor())
                .build()
        )
        .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
        .build()

    @Singleton
    @Provides
    fun provideAccountApi(retrofit: Retrofit): AccountsApi =
        retrofit.create(AccountsApi::class.java)

    @Singleton
    @Provides
    fun provideCatalogApi(retrofit: Retrofit): CatalogApi = retrofit.create(CatalogApi::class.java)

    @Singleton
    @Provides
    fun provideResultApi(retrofit: Retrofit): ResultApi = retrofit.create(ResultApi::class.java)

    @Singleton
    @Provides
    fun provideRedactorApi(retrofit: Retrofit): RedactorApi =
        retrofit.create(RedactorApi::class.java)
}