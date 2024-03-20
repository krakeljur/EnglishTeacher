package com.example.data.catalog.sources.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.catalog.entities.room.LessonDbEntity


@Dao
interface LessonDao {

    @Query("SELECT * FROM lesson WHERE is_favorite = :isFavorite" +
            " AND (:searchBy = '' OR name LIKE '%' || :searchBy || '%' OR id LIKE '%' || :searchBy || '%' OR description LIKE '%' || :searchBy || '%')")
    fun getPagingSourceCatalog(isFavorite: Boolean, searchBy : String = ""): PagingSource<Int, LessonDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(lessons: List<LessonDbEntity>)

    @Query("DELETE FROM lesson")
    suspend fun clear()

    @Transaction
    suspend fun refresh(lessons: List<LessonDbEntity>) {
        clear()
        save(lessons)
    }

    suspend fun save(lesson: LessonDbEntity) {
        save(listOf(lesson))
    }


}