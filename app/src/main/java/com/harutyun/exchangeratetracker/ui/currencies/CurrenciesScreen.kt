package com.harutyun.exchangeratetracker.ui.currencies

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.harutyun.exchangeratetracker.R
import com.harutyun.exchangeratetracker.ui.components.AppBar
import com.harutyun.exchangeratetracker.ui.components.DropDownMenu

@Composable
fun CurrenciesScreen() {
    Scaffold(
        topBar = {
            AppBar(title = stringResource(R.string.currencies), showBackButton = false) {

                val items = listOf("USD", "EUR", "JYP", "AMD", "RUB")
                var selectedText by remember { mutableStateOf(items.first()) }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(Modifier.weight(1f)) {
                        DropDownMenu(
                            items = items,
                            initialText = selectedText,
                            onItemSelected = { selectedItem ->
                                selectedText = selectedItem
                            }
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    FilterButton {

                    }
                }
            }
        }
    ) { innerPadding ->
        val currencies = mutableListOf<String>()
        repeat(100) {
            currencies.add("USD $it")
        }
        Column(Modifier.padding(innerPadding)) {
            Currencies(items = currencies, onItemSelected = {})
        }
    }
}

@Composable
fun Currencies(
    items: List<String>,
    onItemSelected: (String) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxWidth(),
        content = {
            items(items.size) { index ->
                val item = items[index]
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item,
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = item + "0",
                                style = MaterialTheme.typography.bodyLarge
                            )

                            IconButton(
                                onClick = { onItemSelected(item) }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_favorites_off),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun FilterButton(
    onClick: () -> Unit
) {
    Button(
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        contentPadding = PaddingValues(1.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = null,
        )
    }
}

