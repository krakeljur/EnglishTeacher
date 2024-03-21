package com.example.englishteacher

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.englishteacher.databinding.FragmentTabsBinding
import com.example.englishteacher.navigation.Router
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TabsFragment : Fragment(R.layout.fragment_tabs) {

    private lateinit var binding: FragmentTabsBinding

    @Inject
    lateinit var router: Router

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTabsBinding.bind(view)


        val navHost = childFragmentManager.findFragmentById(R.id.tabsContainer) as NavHostFragment
        val navController = navHost.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.setOnItemReselectedListener { item ->
            val destinationId = when (item.itemId) {
                R.id.profile_graph -> { R.id.profileFragment }
                R.id.catalog_graph -> { R.id.catalogFragment }
                else -> { return@setOnItemReselectedListener }
            }
            navController.popBackStack(destinationId, false)
        }

    }
}