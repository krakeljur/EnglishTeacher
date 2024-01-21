package com.example.data.catalog.di

import com.example.data.catalog.sources.CatalogDataSource
import com.example.data.catalog.sources.InMemoryCatalogSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface CatalogSourceModule {


    @Binds
    @Singleton
    fun bindCatalogSource(
        inMemoryCatalogSource: InMemoryCatalogSource
    ): CatalogDataSource
}