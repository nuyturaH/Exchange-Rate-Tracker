package com.harutyun.exchangeratetracker.ui.currencies

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.harutyun.exchangeratetracker.R
import com.harutyun.exchangeratetracker.ui.components.AppBar

@Composable
fun FiltersScreen(
    currenciesViesModel: CurrenciesViesModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by currenciesViesModel.uiState.collectAsStateWithLifecycle()

    FiltersContent(
        /*uiState = uiState,*/
        onApply =  {/*currenciesViesModel.sort(uiState.sortOption)*/ onBackClick()},
        onBackClick = onBackClick
    )
}

@Composable
fun FiltersContent(
    uiState: FiltersUiState = FiltersUiState(),
    onApply: () -> Unit,
    onBackClick: () -> Unit
) {

    Scaffold(topBar = {
        AppBar(
            title = stringResource(R.string.filters),
            showBackButton = true,
            onBackClick = onBackClick,
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            var sortOption by remember { mutableStateOf(SortOption.CODE_A_Z) }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = stringResource(R.string.sort_by).uppercase(),
                    style = MaterialTheme.typography.titleSmall,
                )
                SortOptions(sortOption) { option -> sortOption = option }
            }

            Button(
                onClick = { onApply() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(stringResource(R.string.apply))
            }

        }
    }

}


@Composable
fun SortOptions(
    selectedOption: SortOption,
    onOptionSelected: (SortOption) -> Unit
) {
    val sortOptions = SortOption.values()

    Column {
        sortOptions.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        selected = (option == selectedOption),
                        onClick = { onOptionSelected(option) },
                        role = Role.RadioButton
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = when (option) {
                        SortOption.CODE_A_Z -> stringResource(R.string.code_a_z)
                        SortOption.CODE_Z_A -> stringResource(R.string.code_z_a)
                        SortOption.QUOTE_ASC -> stringResource(R.string.quote_asc)
                        SortOption.QUOTE_DESC -> stringResource(R.string.quote_desc)
                    },
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = { onOptionSelected(option) }
                )
            }
        }
    }
}