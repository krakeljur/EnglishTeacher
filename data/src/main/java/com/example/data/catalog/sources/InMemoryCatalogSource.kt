package com.example.data.catalog.sources

import com.example.data.catalog.entities.LessonDataEntity
import com.example.data.catalog.entities.WordDataEntity
import javax.inject.Inject

class InMemoryCatalogSource @Inject constructor() : CatalogDataSource {


    private val catalogList: MutableList<LessonDataEntity> =
        mutableListOf(
            LessonDataEntity(
                "TESTLESSON",
                "ITS TEST LESSON NUMBER 1",
                1
            ),
            LessonDataEntity(
                "AXAXAX",
                "XAXAXAXAX",
                2
            ),
        )

    private val favoriteList: MutableList<LessonDataEntity> =
        mutableListOf(
            LessonDataEntity(
                "AXAXAX",
                "XAXAXAXAX",
                2
            )
        )


    private val words: MutableList<WordDataEntity> =
        mutableListOf(
            WordDataEntity(
                "Корабль",
                "Ship",
                1
            ),
            WordDataEntity(
                "Мама",
                "Mother",
                1
            ),
            WordDataEntity(
                "Папа",
                "Father",
                1
            ),
            WordDataEntity(
                "Дверь",
                "Door",
                2
            ),
            WordDataEntity(
                "Лампа",
                "Lamp",
                2
            ),
            WordDataEntity(
                "Мяч",
                "Ball",
                2
            ),
        )

    override fun getCatalog(): List<LessonDataEntity> {
        return catalogList
    }

    override fun getFavorite(): List<LessonDataEntity> {
        return favoriteList
    }

    override suspend fun addFavorite(idLesson: Long) {

        val lesson = catalogList.firstOrNull {
            it.id == idLesson
        }

        if (lesson != null && lesson !in favoriteList) {
            favoriteList.add(lesson)
        }

    }

    override suspend fun deleteFavorite(idLesson: Long) {

        favoriteList.removeIf { it.id == idLesson }

    }

    override fun getWords(idLesson: Long): List<WordDataEntity> {
        return words.filter { it.idLesson == idLesson }
    }

    override fun getLesson(lessonId: Long): LessonDataEntity {
        return catalogList.firstOrNull { it.id == lessonId } ?: LessonDataEntity("UNKNOWN", "UNKNOWN", 1000L)
    }
}