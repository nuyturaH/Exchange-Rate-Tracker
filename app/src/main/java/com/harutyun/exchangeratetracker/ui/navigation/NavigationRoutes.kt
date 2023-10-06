package com.harutyun.exchangeratetracker.ui.navigation

import androidx.annotation.StringRes
import com.harutyun.exchangeratetracker.R


sealed class NavigationRoutes(val route: String) {
    data object MainNavGraph : NavigationRoutes("mainNavGraph")
    data object CurrenciesNavGraph : NavigationRoutes("currenciesNavGraph")

    data object Filters : NavigationRoutes("filters")
}

sealed class BottomNavItem(@StringRes var title: Int, var icon: Int, var route: String) {
    data object Currencies :
        BottomNavItem(R.string.currencies, R.drawable.ic_currency, "currencies")

    data object Favourites :
        BottomNavItem(R.string.favorites, R.drawable.ic_favorites_on, "favorites")
}