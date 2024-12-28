package com.example.storyapp

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.ui.home.HomeViewModel
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = binding.topAppBar
        setSupportActionBar(toolbar)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.mapsFragment, R.id.uploadStoryFragment, R.id.profileFragment),
            binding.drawerLayout
        )

        setupNavigationMenu()
        setupBottomNavigationMenu()

        navController.addOnDestinationChangedListener { _, navDestination, _ ->
            val shouldHide = navDestination.id == R.id.registerFragment || navDestination.id == R.id.loginFragment || navDestination.id == R.id.detailFragment
            showHideNavigation(shouldHide)
        }

    }

    private fun showHideNavigation(hide: Boolean) {
        toolbar.visibility = if (hide) View.GONE else View.VISIBLE
        binding.bottomNavigation?.visibility = if (hide) View.GONE else View.VISIBLE
        binding.navView?.visibility = if (hide) View.GONE else View.VISIBLE
        binding.drawerLayout?.apply {
            if (hide) setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            else setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }

    private fun setupBottomNavigationMenu() {
        val bottomNavigationView = binding.bottomNavigation
        bottomNavigationView?.setupWithNavController(navController)
    }

    private fun setupNavigationMenu() {
        val sideNavView = binding.navView
        sideNavView?.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
    }

}
