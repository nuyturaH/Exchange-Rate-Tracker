package com.harutyun.exchangeratetracker.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.harutyun.exchangeratetracker.ui.currencies.CurrenciesScreen
import com.harutyun.exchangeratetracker.ui.currencies.FiltersScreen
import com.harutyun.exchangeratetracker.ui.favorites.FavoritesScreen

@Composable
fun NavigationGraph(navController: NavHostController, padding: PaddingValues) {
    NavHost(
        modifier = Modifier.padding(padding),
        navController = navController,
        route = NavigationRoutes.MainNavGraph.route,
        startDestination = BottomNavItem.Currencies.route
    ) {
        composable(route = BottomNavItem.Currencies.route) {
            CurrenciesScreen(
                onFilterClick = { navController.navigate(NavigationRoutes.Filters.route) },
                currenciesViesModel = it.sharedViewModel(navController)
            )
        }

        composable(route = NavigationRoutes.Filters.route) {
            FiltersScreen(
                onBackClick = { navController.navigateUp() },
                currenciesViesModel = it.sharedViewModel(navController)
            )
        }

        composable(BottomNavItem.Favourites.route) {
            FavoritesScreen()
        }
    }
}


@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}