package com.example.catalog.presentation

import android.os.Bundle

interface CatalogRouter {

    fun launchBackFromCard()

    fun launchGameFromCard(idLesson: Long)

    fun launchCardFromCatalog(idLesson: Long)

    fun getCardArgs(args: Bundle) : Long

}