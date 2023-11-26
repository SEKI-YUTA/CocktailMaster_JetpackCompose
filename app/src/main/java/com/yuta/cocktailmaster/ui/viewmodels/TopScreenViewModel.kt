package com.yuta.cocktailmaster.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuta.cocktailmaster.ui.model.CocktailIngredient_UI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TopScreenViewModel() : ViewModel() {
    private val _viewState = MutableStateFlow(TopScreenViewState.INITIAL)
    val viewState = _viewState.asStateFlow()

    companion object {
        fun provideFactory(): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TopScreenViewModel() as T
                }
            }
        }
    }

    fun onIngredientDeleteRequest(ingredient: CocktailIngredient_UI) {
        _viewState.value = _viewState.value.copy(
            isShowingDeleteConfirmDialog = true,
            selectedIngredient = ingredient,
        )
    }

    fun onCloseDeleteConfirmDialog() {
        _viewState.value = _viewState.value.copy(
            selectedIngredient = null,
            isShowingDeleteConfirmDialog = false,
        )
    }

    fun onIngredientEditRequest(ingredient: CocktailIngredient_UI) {
        _viewState.value = _viewState.value.copy(
            isShowingEditDialog = true,
            selectedIngredient = ingredient,
        )
    }

    fun onCloseEditDialog() {
        _viewState.value = _viewState.value.copy(
            selectedIngredient = null,
            isShowingEditDialog = false,
        )
    }

    fun onUpdateUserInput(userInputState: AddCocktailIngredientScreenViewModel.UserInputState) {
        println("onUpdateUserInput")
        _viewState.value = _viewState.value.copy(
            userInputState = userInputState,
        )
    }

    fun onResetUserInput() {
        _viewState.value = _viewState.value.copy(
            userInputState = AddCocktailIngredientScreenViewModel.UserInputState(),
        )
    }

    data class TopScreenViewState(
        val isShowingEditDialog: Boolean = false,
        val isShowingDeleteConfirmDialog: Boolean = false,
        val selectedIngredient: CocktailIngredient_UI? = null,
        val userInputState: AddCocktailIngredientScreenViewModel.UserInputState =
            AddCocktailIngredientScreenViewModel.UserInputState(),
    ) {
        companion object {
            val INITIAL = TopScreenViewState()
        }
    }
}