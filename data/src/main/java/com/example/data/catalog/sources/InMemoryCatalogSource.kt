package com.example.data.catalog.sources

import com.example.common.Container
import com.example.data.catalog.entities.LessonDataEntity
import com.example.data.catalog.entities.WordDataEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryCatalogSource() : CatalogDataSource {


    private val catalogFlow: MutableStateFlow<Container<List<LessonDataEntity>>> = MutableStateFlow(
        Container.Success(
            listOf(
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
        )
    )


    private val favoriteFlow: MutableStateFlow<Container<List<LessonDataEntity>>> =
        MutableStateFlow(
            Container.Success(
                listOf(
                    LessonDataEntity(
                        "AXAXAX",
                        "XAXAXAXAX",
                        2
                    )
                )
            )
        )


    private val words: MutableStateFlow<Container<List<WordDataEntity>>> = MutableStateFlow(
        Container.Success(
            listOf(
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
        )
    )


    override fun getCatalog(): Flow<Container<List<LessonDataEntity>>> {
        return catalogFlow.asStateFlow()
    }

    override fun getFavorite(): Flow<Container<List<LessonDataEntity>>> {
        return favoriteFlow.asStateFlow()
    }

    override suspend fun addFavorite(idLesson: Long) {
        val oldFavorites = (favoriteFlow.value as Container.Success).data

        favoriteFlow.emit(Container.Pending)

        val lesson = (catalogFlow.value as Container.Success).data.find {
            it.id == idLesson
        }

        if (lesson != null) {
            favoriteFlow.emit(Container.Success(oldFavorites + lesson))
        } else {
            favoriteFlow.emit(Container.Error("BAD ID LESSON"))
        }

    }

    override suspend fun deleteFavorite(idLesson: Long) {
        val oldFavorites = (favoriteFlow.value as Container.Success).data

        favoriteFlow.emit(Container.Pending)

        val newFavorites = oldFavorites.toMutableList()

        newFavorites.removeIf {
            it.id == idLesson
        }

        favoriteFlow.emit(Container.Success(newFavorites))

    }

    override fun getWords(idLesson: Long): Flow<Container<List<WordDataEntity>>> {
        val newFlow = (words.value as Container.Success).data.filter { it.idLesson == idLesson }
        return MutableStateFlow(Container.Success(newFlow)).asStateFlow()
    }
}