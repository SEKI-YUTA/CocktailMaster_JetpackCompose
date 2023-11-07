package com.example.cocktailmaster.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cocktailmaster.data.interfaces.CocktailApiRepository
import com.example.cocktailmaster.ui.model.CocktailIngredient_UI
import com.example.cocktailmaster.ui.model.Cocktail_UI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class CraftableCocktailListScreenEvent {
    object FetchCocktailData: CraftableCocktailListScreenEvent()
}

class CraftableCocktailListScreenViewModel(
    val apiRepository: CocktailApiRepository,
    val ingredientList: List<CocktailIngredient_UI>
): ViewModel() {
    private val _viewState = MutableStateFlow(CraftableCocktailListScreenViewState.INITIAL)
    val viewState = _viewState.asStateFlow()

    init {
        println("init")
        onEvent(CraftableCocktailListScreenEvent.FetchCocktailData)
    }

    fun onEvent(event: CraftableCocktailListScreenEvent): Job {
        val job = viewModelScope.launch(Dispatchers.IO) {
            when (event) {
                is CraftableCocktailListScreenEvent.FetchCocktailData -> {
                    _viewState.value = _viewState.value.copy(
                        isLoading = true,
                    )
                    listOf(
                        async {
                            fetchCraftableCocktail(ingredientList = ingredientList.map { it.longName })
                        },
                        async {
                          fetchAllCocktail()
                        }
                    ).awaitAll()
                    _viewState.value = _viewState.value.copy(
                        isLoading = false,
                    )
                }
            }
        }
        return job
    }

    fun setSelectedTab(tab: TabItems) {
        _viewState.value = _viewState.value.copy(
            selectedTab = tab
        )
    }

    private suspend fun fetchCraftableCocktail(
        ingredientList: List<String>
    ) {
        val tmp = apiRepository.craftableCocktails(ingredientList).map { it.toUIModel() }
        _viewState.value = _viewState.value.copy(
            craftableCocktailList = tmp,
        )
    }

    private suspend fun fetchAllCocktail() {
        val tmp = apiRepository.getAllCocktails().map { it.toUIModel() }
        _viewState.value = _viewState.value.copy(
            allCocktailList = tmp,
        )
    }

    companion object {
        val allTabs = listOf(
            TabItems.CRAFTABLE,
            TabItems.ALL_COCKTAILS,
        )
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
        val selectedTab: TabItems,
        val craftableCocktailList: List<Cocktail_UI>,
        val allCocktailList: List<Cocktail_UI>
    ) {
        companion object {
            val INITIAL = CraftableCocktailListScreenViewState(
                isLoading = false,
                isFetchFailed = false,
                selectedTab = TabItems.CRAFTABLE,
                craftableCocktailList = emptyList(),
                allCocktailList = emptyList()
            )
        }
    }

}

sealed class TabItems(val idx: Int, val title: String) {
    object CRAFTABLE: TabItems(0, "作れるカクテル")
    object ALL_COCKTAILS: TabItems(1, "すべてのカクテル")
}
