package com.harutyun.exchangeratetracker.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.harutyun.exchangeratetracker.R
import com.harutyun.exchangeratetracker.ui.currencies.CurrenciesScreen
import com.harutyun.exchangeratetracker.ui.currencies.FiltersScreen

@Composable
fun NavigationGraph(navController: NavHostController, padding: PaddingValues) {
    NavHost(
        modifier = Modifier.padding(padding),
        navController = navController,
        route = "currenciesBase",
        startDestination = BottomNavItem.Currencies.route
    ) {
        composable(BottomNavItem.Currencies.route) {
            CurrenciesScreen(onFilterClick = {
                navController.navigate("details")
            })
        }
        composable(BottomNavItem.Favourites.route) {
//            Todo FavouritesScreen()
        }
        detailsNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = "details",
        startDestination = CurrenciesRoutes.Filters.route
    ) {
        composable(route = CurrenciesRoutes.Filters.route) {
            FiltersScreen(onBackClick = {
                navController.navigateUp()
            })
        }

    }
}

sealed class CurrenciesRoutes(@StringRes var title: Int, var route: String) {
    data object Filters : CurrenciesRoutes(R.string.filters, "filters")
}