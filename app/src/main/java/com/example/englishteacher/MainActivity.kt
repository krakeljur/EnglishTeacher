package com.example.englishteacher

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
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

    private val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
        val withoutBottom = setOf(R.id.signUpFragment)
        val withoutBottomAndTool = setOf(R.id.signInFragment, R.id.gameFragment )

        when (destination.id) {
            in withoutBottomAndTool -> {
                binding.toolbar.visibility = View.GONE
                binding.bottomNavigationView.visibility = View.GONE
            }
            in withoutBottom -> {
                binding.bottomNavigationView.visibility = View.GONE
                binding.toolbar.visibility = View.VISIBLE
            }
            else -> {
                binding.bottomNavigationView.visibility = View.VISIBLE
                binding.toolbar.visibility = View.VISIBLE
            }
        }
    }


override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)

    val navHostFragment =
        supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
                as NavHostFragment

    val navController = navHostFragment.navController

    val appBarConfiguration = AppBarConfiguration(setOf(R.id.profileFragment, R.id.catalogFragment))
    binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    binding.bottomNavigationView.setupWithNavController(navController)

    router.navController = navController
    setupScreensListener()

    setContentView(binding.root)
}

private fun setupScreensListener() {

    router.navController?.addOnDestinationChangedListener(listener)
}

override fun onDestroy() {
    router.navController?.removeOnDestinationChangedListener(listener)
    router.navController = null
    super.onDestroy()
}
}