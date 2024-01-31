package com.example.game.presentation

import android.os.Bundle

interface GameRouter {

    fun returnToCardFromGame()

    fun getGameArgs(args: Bundle) : Long
}