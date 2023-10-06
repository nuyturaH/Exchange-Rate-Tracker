package com.harutyun.exchangeratetracker.ui.currencies

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.harutyun.domain.model.Currency
import com.harutyun.exchangeratetracker.R
import com.harutyun.exchangeratetracker.ui.components.AppBar
import com.harutyun.exchangeratetracker.ui.components.DropDownMenu
import com.harutyun.exchangeratetracker.ui.theme.TextDefaultBlack
import com.harutyun.exchangeratetracker.ui.theme.Yellow

@Composable
fun CurrenciesScreen(currenciesViesModel: CurrenciesViesModel = hiltViewModel(), onFilterClick: () -> Unit) {
    val uiState by currenciesViesModel.uiState.collectAsStateWithLifecycle()

    CurrenciesContent(
        uiState = uiState,
        onRetry = { currenciesViesModel.getExchangeRates(uiState.baseCurrencyName) },
        onBaseCurrencySelected = { selectedItem -> currenciesViesModel.getExchangeRates(selectedItem) },
        onFilterClick = onFilterClick
    )
}

@Composable
private fun CurrenciesContent(
    uiState: CurrenciesUiState,
    onBaseCurrencySelected: (String) -> Unit,
    onRetry: () -> Unit,
    onFilterClick: () -> Unit
) {
    Scaffold(topBar = {
        AppBar(title = stringResource(R.string.currencies)) {
            var items: List<String> = listOf()

            when (uiState.currencyListUiState) {
                is CurrencyListUiState.Success -> items = uiState.currenciesDropDown
                else -> {}
            }

            var selectedText by remember { mutableStateOf(uiState.baseCurrencyName) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DropDownMenu(
                    modifier = Modifier.weight(1f),
                    items = items,
                    initialText = selectedText,
                    onItemSelected = { selectedItem ->
                        selectedText = selectedItem
                        onBaseCurrencySelected(selectedItem)
                    })
                Spacer(modifier = Modifier.width(8.dp))

                FilterButton {
                    onFilterClick()
                }
            }
        }
    }) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            when (uiState.currencyListUiState) {
                is CurrencyListUiState.Loading -> CurrenciesShimmer()
                is CurrencyListUiState.Error -> ErrorScreen(uiState.currencyListUiState.errorMessage, onRetry)
                is CurrencyListUiState.Success -> Currencies(
                    items = uiState.currencyListUiState.currencyList,
                    onItemFavoriteClicked = {})
            }

        }
    }
}

@Composable
fun Currencies(items: List<Currency>, onItemFavoriteClicked: (Currency) -> Unit) {
    AnimatedVisibility(visible = true) {
        val itemList = remember { items.toMutableStateList() }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxWidth(),
            content = {
                items(count = itemList.size, key = { a -> itemList[a].name }) { index ->
                    val item = itemList[index]
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
                                text = item.name,
                                modifier = Modifier.padding(8.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = item.rate.toString(),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = TextDefaultBlack
                                )

                                IconButton(onClick = {
                                    val i = itemList.indexOf(item)
                                    itemList[i] = itemList[i].copy(isFavorite = !item.isFavorite)

                                    onItemFavoriteClicked(item)
                                }) {
                                    Icon(
                                        painter = if (item.isFavorite) painterResource(id = R.drawable.ic_favorites_on)
                                        else painterResource(id = R.drawable.ic_favorites_off),
                                        contentDescription = null,
                                        tint = if (item.isFavorite) Yellow else MaterialTheme.colorScheme.secondary
                                    )
                                }
                            }
                        }
                    }
                }
            })
    }
}

@Composable
fun CurrenciesShimmer() {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxWidth(),
        content = {
            items(10) { _ ->
                ShimmerListItem(isLoading = true)
            }
        })
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
            containerColor = Color.White, contentColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = null,
        )
    }
}

@Composable
fun ErrorScreen(message: String, onRetryClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message)
        Spacer(Modifier.height(16.dp))
        Button(onClick = { onRetryClicked() }) {
            Text(stringResource(R.string.retry))
        }
    }
}

