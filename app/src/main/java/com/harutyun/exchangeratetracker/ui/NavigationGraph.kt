package com.harutyun.exchangeratetracker.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.harutyun.exchangeratetracker.ui.currencies.CurrenciesScreen

@Composable
fun NavigationGraph(navController: NavHostController, padding: PaddingValues) {
    NavHost(
        modifier = Modifier.padding(padding),
        navController = navController,
        startDestination = BottomNavItem.Currencies.screen_route) {
        composable(BottomNavItem.Currencies.screen_route) {
            CurrenciesScreen()
        }
        composable(BottomNavItem.Favourites.screen_route) {
//            Todo FavouritesScreen()
        }
    }
}