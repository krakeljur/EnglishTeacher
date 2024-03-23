package com.example.englishteacher

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.englishteacher.databinding.ActivityMainBinding
import com.example.englishteacher.navigation.Router
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var router: Router

    private lateinit var binding: ActivityMainBinding
    private val appBarConfiguration =
        AppBarConfiguration(setOf(R.id.profileFragment, R.id.catalogFragment))

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {

        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            if (f is TabsFragment || f is NavHostFragment) return
            activateNewNavController(f.findNavController())
        }
    }


    private val destinationListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            val withoutTool = setOf(R.id.signInFragment, R.id.gameFragment, R.id.profileFragment)

            when (destination.id) {
                in withoutTool -> {
                    binding.toolbar.visibility = View.GONE
                }

                else -> {
                    setSupportActionBar(binding.toolbar)
                    binding.toolbar.setupWithNavController(
                        router.navController!!,
                        appBarConfiguration
                    )
                    binding.toolbar.visibility = View.VISIBLE
                }
            }
            supportActionBar?.title = destination.label

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, true)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
                    as NavHostFragment

        val navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar)
        activateNewNavController(navController)


        setContentView(binding.root)
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
        router.navController?.removeOnDestinationChangedListener(destinationListener)
        router.navController = null
        super.onDestroy()
    }


    private fun activateNewNavController(navController: NavController) {
        if (router.navController == navController) return
        router.navController?.removeOnDestinationChangedListener(destinationListener)
        navController.addOnDestinationChangedListener(destinationListener)
        router.navController = navController
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }


}