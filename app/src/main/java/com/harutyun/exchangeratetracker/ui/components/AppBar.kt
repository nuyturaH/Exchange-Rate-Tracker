package com.harutyun.exchangeratetracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


/**
 * Custom AppBar Composable.
 *
 * If in the future [TopAppBar] will not be [ExperimentalMaterial3Api],
 * it will be good to use it instead of this custom AppBar.
 *
 * @param title The title text to display.
 * @param showBackButton Whether to show the back button.
 * @param onBackClick Callback for when the back button is clicked.
 * @param content Custom content to display within the AppBar.
 */

@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    title: String,
    showBackButton: Boolean,
    onBackClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.primaryContainer),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.padding(
                start = if (showBackButton) 4.dp else 16.dp,
                top = 10.dp,
                end = 16.dp,
                bottom = 10.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBackButton) {
                IconButton(onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = "Go back",
                    )
                }
            }
            Text(
                text = title, style = MaterialTheme.typography.headlineLarge
            )
        }
        Box(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            content()
        }
        Divider(color = MaterialTheme.colorScheme.outline)
    }
}