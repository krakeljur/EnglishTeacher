package com.example.data.accounts.di

import com.example.data.AccountsDataRepository
import com.example.data.accounts.AccountsDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AccountRepositoriesModule {

    @Binds
    @Singleton
    fun bindAccountRepository(
        accountsDataRepository: AccountsDataRepositoryImpl
    ) : AccountsDataRepository
}