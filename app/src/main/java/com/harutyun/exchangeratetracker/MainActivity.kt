package com.harutyun.exchangeratetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.harutyun.exchangeratetracker.ui.components.BottomNavigationBar
import com.harutyun.exchangeratetracker.ui.navigation.BottomNavItem
import com.harutyun.exchangeratetracker.ui.navigation.NavigationGraph
import com.harutyun.exchangeratetracker.ui.theme.ExchangeRateTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExchangeRateTrackerTheme {
                val navController = rememberNavController()
                val screens = listOf(BottomNavItem.Currencies, BottomNavItem.Favourites)
                val showBottomBar =
                    navController.currentBackStackEntryAsState().value?.destination?.route in screens.map { it.route }
                Scaffold(
                    bottomBar = {
                            AnimatedVisibility(visible = showBottomBar) {
                                BottomNavigationBar(
                                    listOf(BottomNavItem.Currencies, BottomNavItem.Favourites)
                                )
                        }
                    },
                ) { innerPadding ->
                    NavigationGraph(navController = navController, padding = innerPadding)
                }
            }
        }
    }
}
