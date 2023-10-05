package com.harutyun.exchangeratetracker.ui.currencies

data class FiltersUiState(
    val sortOption: SortOption = SortOption.CODE_A_Z
)

enum class SortOption {
    CODE_A_Z, CODE_Z_A, QUOTE_ASC, QUOTE_DESC
}