package com.example.data.base.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.catalog.entities.room.LessonDbEntity
import com.example.data.catalog.sources.dao.LessonDao
import com.example.data.game.etities.room.ResultDbEntity
import com.example.data.game.sources.dao.ResultDao


@Database(
    version = 1,
    entities = [
        LessonDbEntity::class,
        ResultDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getLessonDao() : LessonDao

    abstract fun getResultDao() : ResultDao
}