package com.example.cocktailmaster.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailmaster.data.interfaces.CocktailApiRepository
import com.example.cocktailmaster.ui.model.Cocktail_UI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class CraftableCocktailListScreenEvent {
    data class FetchCraftableCocktailList(
        val apiRepository: CocktailApiRepository,
        val ingredientList: List<String>
    ): CraftableCocktailListScreenEvent()
}

class CraftableCocktailListScreenViewModel: ViewModel() {
    val _viewState = MutableStateFlow(CraftableCocktailListScreenViewState.INITIAL)
    val viewState = _viewState.asStateFlow()

    fun onEvent(event: CraftableCocktailListScreenEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            when (event) {
                is CraftableCocktailListScreenEvent.FetchCraftableCocktailList -> {
                    _viewState.value = _viewState.value.copy(
                        isLoading = true,
                    )
                    async {fetchCraftableCocktail(
                        apiRepository = event.apiRepository,
                        ingredientList = event.ingredientList
                    )}.await()
                    _viewState.value = _viewState.value.copy(
                        isLoading = false,
                    )
                }
            }
        }
    }

    private suspend fun fetchCraftableCocktail(
        apiRepository: CocktailApiRepository,
        ingredientList: List<String>
    ) {
        val tmp = apiRepository.craftableCocktails(ingredientList).map { it.toUIModel() }
        _viewState.value = _viewState.value.copy(
            craftableCocktailList = tmp,
        )
    }
    data class CraftableCocktailListScreenViewState(
        val isLoading: Boolean,
        val isFetchFailed: Boolean,
        val craftableCocktailList: List<Cocktail_UI>
    ) {
        companion object {
            val INITIAL = CraftableCocktailListScreenViewState(
                isLoading = false,
                isFetchFailed = false,
                craftableCocktailList = emptyList()
            )
        }
    }
}