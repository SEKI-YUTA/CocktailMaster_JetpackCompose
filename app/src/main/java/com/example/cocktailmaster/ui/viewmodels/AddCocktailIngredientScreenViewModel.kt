package com.example.cocktailmaster.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cocktailmaster.data.interfaces.CocktailApiRepository
import com.example.cocktailmaster.data.repository.CocktailApiRepository_Impl
import com.example.cocktailmaster.ui.model.CocktailIngredient_UI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddCocktailIngredientScreenViewModel(
    val apiRepository: CocktailApiRepository,
): ViewModel() {
    private val _viewState = MutableStateFlow(AddCocktailIngredientScreenViewState.INITIAL)
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchAllIngredientsFromAPI()
        }
    }
    companion object {
        fun provideFactory(
        ): ViewModelProvider.Factory {
            return object: ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AddCocktailIngredientScreenViewModel(
                        apiRepository = CocktailApiRepository_Impl()
                    ) as T
                }
            }
        }
    }

    suspend fun fetchAllIngredientsFromAPI() {
        _viewState.value = _viewState.value.copy(
            isLoading = true,
        )
        val tmp = apiRepository.getAllIngredients().map { it.toUIModel() }
        _viewState.value = _viewState.value.copy(
            isLoading = false,
            ingredientList = tmp,
        )
    }

    data class AddCocktailIngredientScreenViewState(
        val isLoading: Boolean = false,
        val isFetchFailed: Boolean = false,
        val ingredientList: List<CocktailIngredient_UI>,
        val ownedIngredientList: List<CocktailIngredient_UI>,
    ) {
        companion object {
            val INITIAL = AddCocktailIngredientScreenViewState(
                ingredientList = emptyList(),
                ownedIngredientList = emptyList(),
            )
        }
    }
}