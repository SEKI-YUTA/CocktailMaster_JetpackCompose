package com.yuta.cocktailmaster.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yuta.cocktailmaster.data.interfaces.CocktailApiRepository
import com.yuta.cocktailmaster.ui.model.CocktailIngredient_UI
import com.yuta.cocktailmaster.ui.model.Cocktail_UI
import kotlinx.coroutines.Dispatchers
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
        viewModelScope.launch(Dispatchers.IO) {
            onEvent(CraftableCocktailListScreenEvent.FetchCocktailData)
        }
    }

    suspend fun onEvent(event: CraftableCocktailListScreenEvent) {
        when (event) {
            is CraftableCocktailListScreenEvent.FetchCocktailData -> {
                fetchCraftableCocktail(ingredientList = ingredientList.map { it.longName })
                fetchAllCocktail()
            }
        }
    }

    fun setSelectedTab(tab: TabItems) {
        _viewState.value = _viewState.value.copy(
            selectedTab = tab
        )
    }

    private suspend fun fetchCraftableCocktail(
        ingredientList: List<String>
    ) {
        _viewState.value = _viewState.value.copy(
            isCraftableCocktailFetching = true
        )
        val tmp = apiRepository.craftableCocktails(ingredientList)
        _viewState.value = _viewState.value.copy(
            craftableCocktailList = tmp,
            isCraftableCocktailFetching = false

        )
    }

    private suspend fun fetchAllCocktail() {
        if(fetchedAllCocktailList.isNotEmpty()) {
            _viewState.value = _viewState.value.copy(
                allCocktailList = fetchedAllCocktailList
            )
            return
        }
        _viewState.value = _viewState.value.copy(
            isAllCocktailFetching = true
        )
        val tmp = apiRepository.getAllCocktails()
        fetchedAllCocktailList = tmp
        _viewState.value = _viewState.value.copy(
            allCocktailList = tmp,
        )
        _viewState.value = _viewState.value.copy(
            isAllCocktailFetching = false
        )
    }

    companion object {
        val allTabs = listOf(
            TabItems.CRAFTABLE,
            TabItems.ALL_COCKTAILS,
        )
        var fetchedAllCocktailList = emptyList<Cocktail_UI>()
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
        val isCraftableCocktailFetching: Boolean,
        val isAllCocktailFetching: Boolean,
        val isFetchFailed: Boolean,
        val selectedTab: TabItems,
        val craftableCocktailList: List<Cocktail_UI>,
        val allCocktailList: List<Cocktail_UI>
    ) {
        companion object {
            val INITIAL = CraftableCocktailListScreenViewState(
                isCraftableCocktailFetching = false,
                isAllCocktailFetching = false,
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
