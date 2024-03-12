package com.example.catalog.presentation

import android.os.Bundle
import com.example.catalog.domain.entities.LessonData

interface CatalogRouter {

    fun launchBackFromCard()

    fun launchGameFromCard(idLesson: String)

    fun launchCardFromCatalog(lesson: LessonData)

    fun getCardArgs(args: Bundle) : LessonData

}