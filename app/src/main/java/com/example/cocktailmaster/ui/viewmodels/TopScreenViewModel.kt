package com.example.cocktailmaster.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cocktailmaster.data.interfaces.OwnedIngredientRepository
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


    data class TopScreenViewState(
        val isLoading: Boolean = false,
        val isNetworkConnected: Boolean = true,
    ) {
        companion object {
            val INITIAL = TopScreenViewState(
                isLoading = false
            )
        }
    }
}