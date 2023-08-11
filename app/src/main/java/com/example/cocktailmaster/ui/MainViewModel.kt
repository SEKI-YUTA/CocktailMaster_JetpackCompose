package com.example.cocktailmaster.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cocktailmaster.CocktailMasterApplication
import com.example.cocktailmaster.data.AppContainer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
    context: Context
): ViewModel() {
    private val _currentScreen = MutableStateFlow(Screen.TopScreen)
    val currentScreen = _currentScreen.asStateFlow()

    fun setCurrentScreen(screen: Screen) {
        _currentScreen.value = screen
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val tmp: Context = (this[APPLICATION_KEY] as CocktailMasterApplication).appContainer.context
                MainViewModel(tmp)
            }
        }
    }
}