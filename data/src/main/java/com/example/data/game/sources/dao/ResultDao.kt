package com.example.data.game.sources.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.game.etities.room.ResultDbEntity

@Dao
interface ResultDao {

    @Query("SELECT * FROM result WHERE (:idLesson = '') OR (id_lesson = :idLesson)")
    fun getPagingSource(idLesson: String): PagingSource<Int, ResultDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(results: List<ResultDbEntity>)

    @Query("DELETE FROM result")
    suspend fun clear()

    @Transaction
    suspend fun refresh(results: List<ResultDbEntity>) {
        clear()
        save(results)
    }

    suspend fun save(result: ResultDbEntity) {
        save(listOf(result))
    }


}