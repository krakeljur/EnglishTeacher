package com.example.data.base.room.di

import android.content.Context
import androidx.room.Room
import com.example.common.Const.DB_NAME
import com.example.data.base.room.AppDatabase
import com.example.data.catalog.sources.dao.LessonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DatabaseModule {


    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DB_NAME
        ).build()
    }


    @Singleton
    @Provides
    fun provideLessonDao(database: AppDatabase): LessonDao = database.getLessonDao()
}