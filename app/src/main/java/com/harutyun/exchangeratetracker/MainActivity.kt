package com.harutyun.exchangeratetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.harutyun.exchangeratetracker.ui.BottomNavItem
import com.harutyun.exchangeratetracker.ui.NavigationGraph
import com.harutyun.exchangeratetracker.ui.components.BottomNavigationBar
import com.harutyun.exchangeratetracker.ui.theme.ExchangeRateTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExchangeRateTrackerTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            listOf(BottomNavItem.Currencies, BottomNavItem.Favourites)
                        )
                    },
                ) { innerPadding ->
                    NavigationGraph(navController = navController, padding = innerPadding)
                }
            }
        }
    }
}
