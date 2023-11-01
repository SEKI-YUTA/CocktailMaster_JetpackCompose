package com.example.cocktailmaster.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cocktailmaster.data.db.AppDatabase
import com.example.cocktailmaster.data.interfaces.CocktailApiRepository
import com.example.cocktailmaster.data.interfaces.OwnedLiqueurRepository
import com.example.cocktailmaster.data.repository.CocktailApiRepository_Impl
import com.example.cocktailmaster.data.repository.OwnedLiqueurRepository_Impl
import com.example.cocktailmaster.ui.Screen
import com.example.cocktailmaster.ui.model.CocktailIngredient_UI
import com.example.cocktailmaster.util.AppUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val context: Context,
    private val cocktailApiRepository: CocktailApiRepository,
    private val ownedLiqueurRepository: OwnedLiqueurRepository,
) : ViewModel() {

    private val _isNetworkConnected = MutableStateFlow(false)
    val isNetworkConnected = _isNetworkConnected.asStateFlow()

    init {
        viewModelScope.launch {
            AppUtil.checkNetworkConnection(context, _isNetworkConnected)
            withContext(Dispatchers.IO) {
                _isLoading.value = true
                val asyncList = listOf(
                    async { fetchAllIngredientsFromAPI() },
                    async { readOwnedIngredientList() },
                )
                asyncList.awaitAll()
                _isLoading.value = false
            }
        }
    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isFetchFailed = MutableStateFlow(false)
    val isFetchFailed = _isFetchFailed.asStateFlow()

    private val _currentScreen = MutableStateFlow(Screen.TopScreen)
    val currentScreen = _currentScreen.asStateFlow()

    private val _ownedCocktailIngredients =
        MutableStateFlow<List<CocktailIngredient_UI>>(emptyList())
    val ownedCocktailIngredients = _ownedCocktailIngredients.asStateFlow()

    private val _ingredientList = MutableStateFlow<List<CocktailIngredient_UI>>(emptyList())
    val ingredientList = _ingredientList.asStateFlow()


    fun setCurrentScreen(screen: Screen) {
        _currentScreen.value = screen
    }

    fun readOwnedIngredientList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                ownedLiqueurRepository.provideAllIngredient().collect {
                    _ownedCocktailIngredients.value =
                        it.map { liqueurData -> liqueurData.toUIModel() }
                }
            }
        }
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

    fun fetchAllIngredientsFromAPI() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isLoading.value = true
                _isFetchFailed.value = false
                val allIngredients = cocktailApiRepository.getAllIngredients()
                if (allIngredients.size > 0 && allIngredients.first().fetchFailed) {
                    println("ingredient fetch failed")
                    _isFetchFailed.value = true
                    _isLoading.value = false
                } else {
                    _ingredientList.value = allIngredients.map { it.toUIModel() }
                    _isFetchFailed.value = false
                    _isLoading.value = false
                }
            }
        }
    }

    companion object {
        fun provideFactory(context: Context): ViewModelProvider.Factory {
            val cocktailApiRepository = CocktailApiRepository_Impl()
            val ownedLiqueurRepository = OwnedLiqueurRepository_Impl(context)
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(
                        context = context,
                        cocktailApiRepository = cocktailApiRepository,
                        ownedLiqueurRepository = ownedLiqueurRepository
                    ) as T
                }
            }
        }
    }
}