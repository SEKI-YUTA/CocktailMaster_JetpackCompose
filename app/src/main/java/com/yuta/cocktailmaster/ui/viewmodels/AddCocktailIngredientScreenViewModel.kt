package com.yuta.cocktailmaster.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yuta.cocktailmaster.data.interfaces.CocktailApiRepository
import com.yuta.cocktailmaster.ui.model.CocktailIngredient_UI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddCocktailIngredientScreenViewModel(
    val apiRepository: CocktailApiRepository,
) : ViewModel() {
    private val _viewState = MutableStateFlow(AddCocktailIngredientScreenViewState.INITIAL)
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchAllIngredientsFromAPI()
        }
    }

    companion object {
        var fetchedAllIngredientList = emptyList<CocktailIngredient_UI>()

        fun provideFactory(
            apiRepository: CocktailApiRepository
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AddCocktailIngredientScreenViewModel(
                        apiRepository = apiRepository
                    ) as T
                }
            }
        }
    }

    suspend fun fetchAllIngredientsFromAPI() {
        if(fetchedAllIngredientList.isNotEmpty()) {
            _viewState.value = _viewState.value.copy(
                ingredientList = fetchedAllIngredientList,
            )
            return
        }
        _viewState.value = _viewState.value.copy(
            isLoading = true,
        )
        val tmp = apiRepository.getAllIngredients()
        _viewState.value = _viewState.value.copy(
            isLoading = false,
            ingredientList = tmp,
        )
    }

    fun onIngredientTapped(ingredient: CocktailIngredient_UI) {
        _viewState.value = _viewState.value.copy(
            selectedIngredient = ingredient,
            isShowingAddDialog = true,
        )
    }

    fun onCloseAddDialog() {
        _viewState.value = _viewState.value.copy(
            selectedIngredient = null,
            isShowingAddDialog = false,
        )
    }

    fun onUpdateUserInput(userInputState: UserInputState) {
        _viewState.value = _viewState.value.copy(
            userInputState = userInputState,
        )
    }

    fun onResetUserInput() {
        _viewState.value = _viewState.value.copy(
            userInputState = UserInputState(),
        )
    }


    data class AddCocktailIngredientScreenViewState(
        val isLoading: Boolean = false,
        val isFetchFailed: Boolean = false,
        val isShowingAddDialog: Boolean = false,
        val userInputState: UserInputState = UserInputState(),
        val ingredientList: List<CocktailIngredient_UI>,
        val selectedIngredient: CocktailIngredient_UI? = null,
        val ownedIngredientList: List<CocktailIngredient_UI>,
    ) {
        companion object {
            val INITIAL = AddCocktailIngredientScreenViewState(
                ingredientList = emptyList(),
                ownedIngredientList = emptyList(),
            )
        }
    }

    data class UserInputState(
        val description: String = ""
    )
}