package com.example.data.base.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.catalog.entities.room.LessonDbEntity
import com.example.data.catalog.sources.dao.LessonDao


@Database(
    version = 1,
    entities = [
        LessonDbEntity::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getLessonDao() : LessonDao
}