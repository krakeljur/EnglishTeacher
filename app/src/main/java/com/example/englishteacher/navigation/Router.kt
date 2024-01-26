package com.example.englishteacher.navigation

import com.example.catalog.presentation.CatalogRouter
import com.example.game.presentation.GameRouter
import com.example.profile.presentation.ProfileRouter
import com.example.sign_in.presentation.SignInRouter
import com.example.sign_up.presentation.SignUpRouter

interface Router : GameRouter, CatalogRouter, ProfileRouter, SignInRouter, SignUpRouter