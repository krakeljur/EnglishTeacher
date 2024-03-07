package com.example.data.accounts.di

import com.example.data.accounts.sources.AccountsNetworkDataSource
import com.example.data.accounts.sources.RetrofitAccountsDataSource
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
        accountDataSource: RetrofitAccountsDataSource
    ) : AccountsNetworkDataSource
}