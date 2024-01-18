package com.example.data.accounts.di

import com.example.data.accounts.sources.AccountsDataSource
import com.example.data.accounts.sources.InMemoryDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AccountSourceModule {

    @Binds
    @Singleton
    fun bindAccountSource(
        accountDataSource: InMemoryDataSourceImpl
    ) : AccountsDataSource
}