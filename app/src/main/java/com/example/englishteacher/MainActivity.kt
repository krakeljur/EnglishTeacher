package com.example.englishteacher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.englishteacher.databinding.ActivityMainBinding
import com.example.englishteacher.navigation.Router

class MainActivity : AppCompatActivity(), Router {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)


        val navController = findNavController(R.id.fragmentContainerView)
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navHostFragmentController = navHostFragment.navController

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navHostFragmentController)

        setContentView(binding.root)
    }

    override fun returnToCardFromGame() {
        TODO("Not yet implemented")
    }

    override fun launchBackFromCard() {
        TODO("Not yet implemented")
    }

    override fun launchGameFromCard() {
        TODO("Not yet implemented")
    }

    override fun launchCardFromCatalog() {
        TODO("Not yet implemented")
    }

    override fun launchStatisticFromProfile() {
        TODO("Not yet implemented")
    }

    override fun goBackToProfileFromStatistic() {
        TODO("Not yet implemented")
    }

    override fun launchSignInFromProfile() {
        TODO("Not yet implemented")
    }

    override fun launchSignUp() {
        TODO("Not yet implemented")
    }

    override fun launchCatalog() {
        TODO("Not yet implemented")
    }

    override fun goBackToSignInFromSignUp() {
        TODO("Not yet implemented")
    }

}