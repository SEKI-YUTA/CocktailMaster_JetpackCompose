package com.example.cocktailmaster.ui.viewmodels

import android.content.Context
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

class MainViewModel(
    private val context: Context,
    private val ownedIngredientRepository: OwnedIngredientRepository,
) : ViewModel() {
    private val _viewState = MutableStateFlow(MainViewModelViewState())
    val viewState = _viewState.asStateFlow()

    init {
        startUp()
    }

    private fun startUp() {
        viewModelScope.launch(Dispatchers.IO) {
            readOwnedIngredient()
            collectOwnedIngredient()
        }
    }

    private suspend fun readOwnedIngredient() {
        withContext(Dispatchers.IO) {
            _viewState.value = _viewState.value.copy(isOwnedIngredientReading = true)
            val tmp = async {
                ownedIngredientRepository.getAllIngredient().map { it.toUIModel() }
            }.await()
            _viewState.value = _viewState.value.copy(
                ownedIngredientList = tmp,
                isOwnedIngredientReading = false,
            )
        }
    }

    private suspend fun collectOwnedIngredient() {
        withContext(Dispatchers.IO) {
            ownedIngredientRepository.provideAllIngredientFlow().collect { list ->
                val tmp = list.map { it.toUIModel() }
                _viewState.value = _viewState.value.copy(
                    ownedIngredientList = tmp
                )
            }
        }
    }

    companion object {
        fun provideFactory(
            context: Context,
            ownedIngredientRepository: OwnedIngredientRepository
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(
                        context = context,
                        ownedIngredientRepository = ownedIngredientRepository
                    ) as T
                }
            }
        }
    }

    data class MainViewModelViewState(
        val isOwnedIngredientReading: Boolean = false,
        val ownedIngredientList: List<CocktailIngredient_UI> = emptyList(),
    )
}