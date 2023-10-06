package com.harutyun.exchangeratetracker.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.harutyun.exchangeratetracker.ui.navigation.BottomNavItem

@Composable
fun BottomNavigationBar(list: List<BottomNavItem>) {
    var selectedItem by remember { mutableIntStateOf(0) }

    Column {
        Divider(color = MaterialTheme.colorScheme.outline)
        NavigationBar(tonalElevation = 0.dp) {
            list.forEachIndexed { index, item ->
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = MaterialTheme.colorScheme.secondary,
                        unselectedTextColor = MaterialTheme.colorScheme.secondary,
                    ),
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = stringResource(id = item.title),
                        )
                    },
                    label = { Text(stringResource(id = item.title)) },
                    selected = selectedItem == index,
                    onClick = { selectedItem = index })
            }
        }
    }

}