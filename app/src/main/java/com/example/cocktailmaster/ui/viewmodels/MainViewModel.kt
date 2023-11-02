package com.example.cocktailmaster.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cocktailmaster.data.db.AppDatabase
import com.example.cocktailmaster.ui.model.CocktailIngredient_UI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val context: Context,
) : ViewModel() {

    private val _ownedCocktailIngredients =
        MutableStateFlow<List<CocktailIngredient_UI>>(emptyList())
    val ownedCocktailIngredients = _ownedCocktailIngredients.asStateFlow()

    fun updateOwnedIngredientList(
        ingredientList: List<CocktailIngredient_UI>
    ) {
        _ownedCocktailIngredients.value = ingredientList
    }

    fun addOwnedIngredient(ingredient: CocktailIngredient_UI) {
        ingredient.id = 0
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                AppDatabase.getDatabase(context).ownedIngredientDao()
                    .insertIngredient(ingredient.toDataModel())
            }
        }
    }

    fun editOwnedIngredient(ingredient: CocktailIngredient_UI) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                AppDatabase.getDatabase(context).ownedIngredientDao()
                    .updateIngredient(ingredient.toDataModel())
            }
        }
    }

    fun deleteOwnedIngredient(ingredient: CocktailIngredient_UI) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                AppDatabase.getDatabase(context).ownedIngredientDao()
                    .deleteIngredient(ingredient.toDataModel())
            }
        }
    }

    companion object {
        fun provideFactory(context: Context): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(
                        context = context,
                    ) as T
                }
            }
        }
    }
}