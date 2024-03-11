package com.example.data.catalog.di

import androidx.paging.ExperimentalPagingApi
import com.example.data.CatalogDataRepository
import com.example.data.catalog.CatalogDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface CatalogRepositoryModule {


    @OptIn(ExperimentalPagingApi::class)
    @Binds
    @Singleton
    fun bindCatalogRepository(
      catalogDataRepositoryImpl: CatalogDataRepositoryImpl
    ) : CatalogDataRepository
}