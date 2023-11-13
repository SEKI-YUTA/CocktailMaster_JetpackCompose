package com.yuta.cocktailmaster.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yuta.cocktailmaster.data.interfaces.CocktailApiRepository
import com.yuta.cocktailmaster.ui.model.CocktailIngredient_UI
import com.yuta.cocktailmaster.ui.model.Cocktail_UI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class CraftableCocktailListScreenEvent {
    object FetchCraftableCocktailList: CraftableCocktailListScreenEvent()
}

class CraftableCocktailListScreenViewModel(
    val apiRepository: CocktailApiRepository,
    val ingredientList: List<CocktailIngredient_UI>
): ViewModel() {
    private val _viewState = MutableStateFlow(CraftableCocktailListScreenViewState.INITIAL)
    val viewState = _viewState.asStateFlow()

    init {
        println("init")
        onEvent(CraftableCocktailListScreenEvent.FetchCraftableCocktailList)
    }

    fun onEvent(event: CraftableCocktailListScreenEvent): Job {
        val job = viewModelScope.launch(Dispatchers.IO) {
            when (event) {
                is CraftableCocktailListScreenEvent.FetchCraftableCocktailList -> {
                    _viewState.value = _viewState.value.copy(
                        isLoading = true,
                    )
                    async {fetchCraftableCocktail(
                        ingredientList = ingredientList.map { it.longName }
                    )}.await()
                    _viewState.value = _viewState.value.copy(
                        isLoading = false,
                    )
                }
            }
        }
        return job
    }

    private suspend fun fetchCraftableCocktail(
        ingredientList: List<String>
    ) {
        val tmp = apiRepository.craftableCocktails(ingredientList)
        _viewState.value = _viewState.value.copy(
            craftableCocktailList = tmp,
        )
    }

    companion object {
        fun provideFactory(
            apiRepository: CocktailApiRepository,
            ingredientList: List<CocktailIngredient_UI>
        ): ViewModelProvider.Factory {
            println(ingredientList)
            return object: ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CraftableCocktailListScreenViewModel(
                        apiRepository = apiRepository,
                        ingredientList = ingredientList
                    ) as T
                }
            }
        }
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