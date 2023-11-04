package com.example.cocktailmaster.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cocktailmaster.data.interfaces.OwnedIngredientRepository
import com.example.cocktailmaster.ui.model.CocktailIngredient_UI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TopScreenViewModel(
    val ownedIngredientRepository: OwnedIngredientRepository,
    val onUpdateOwnedIngredient: (List<CocktailIngredient_UI>) -> Unit
) : ViewModel() {
    val _viewState = MutableStateFlow(TopScreenViewState.INITIAL)
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            readOwnedIngredient()
            collectOwnedIngredient()
        }
    }

    companion object {
        fun provideFactory(
            ownedIngredientRepository: OwnedIngredientRepository,
            onUpdateOwnedIngredient: (List<CocktailIngredient_UI>) -> Unit
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TopScreenViewModel(
                        ownedIngredientRepository = ownedIngredientRepository,
                        onUpdateOwnedIngredient = onUpdateOwnedIngredient
                    ) as T
                }
            }
        }
    }

    suspend fun readOwnedIngredient() {
        withContext(Dispatchers.IO) {
            _viewState.value = _viewState.value.copy(isLoading = true)
            val tmp = async {
                ownedIngredientRepository.getAllIngredient().map { it.toUIModel() }
            }.await()
            _viewState.value = _viewState.value.copy(
                ownedIngredientList = tmp,
                isLoading = false,
            )
            onUpdateOwnedIngredient(tmp)
        }
    }

    suspend fun collectOwnedIngredient() {
        withContext(Dispatchers.IO) {
            ownedIngredientRepository.provideAllIngredientFlow().collect { list ->
                val tmp = list.map { it.toUIModel() }
                _viewState.value = _viewState.value.copy(
                    ownedIngredientList = tmp
                )
                onUpdateOwnedIngredient(tmp)
            }
        }
    }

    data class TopScreenViewState(
        val isLoading: Boolean = false,
        val ownedIngredientList: List<CocktailIngredient_UI> = emptyList(),
        val isNetworkConnected: Boolean = true,
    ) {
        companion object {
            val INITIAL = TopScreenViewState(
                isLoading = false
            )
        }
    }
}