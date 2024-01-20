package com.example.data.settings.di

import com.example.data.settings.SettingsDataSource
import com.example.data.settings.SharedPreferenceDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SettingsSourceModule {

    @Binds
    @Singleton
    fun bindSetting(
        sharedPreferencesSource: SharedPreferenceDataSource
    ) : SettingsDataSource

}