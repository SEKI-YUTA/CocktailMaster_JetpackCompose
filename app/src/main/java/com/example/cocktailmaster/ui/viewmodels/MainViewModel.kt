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
    init {
        viewModelScope.launch {
//            AppUtil.checkNetworkConnection(context, _isNetworkConnected)
            withContext(Dispatchers.IO) {
                val asyncList = listOf(
                    async { fetchAllIngredientsFromAPI() },
                    async { readOwnedIngredientList() },
                )
                asyncList.awaitAll()
            }
        }
    }

    private val _isNetworkConnected = MutableStateFlow(true)
    val isNetworkConnected = _isNetworkConnected.asStateFlow()

    private val _isCocktailIngredientListLoading = MutableStateFlow(false)
    val isCocktailIngredientListLoading = _isCocktailIngredientListLoading.asStateFlow()

    private val _isOwnedIngredientListLoading = MutableStateFlow(false)
    val isOwnedIngredientListLoading = _isOwnedIngredientListLoading.asStateFlow()

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

    suspend fun readOwnedIngredientList() {
        withContext(Dispatchers.IO) {
            _isOwnedIngredientListLoading.value = true
            _ownedCocktailIngredients.value =
                async {
                    ownedLiqueurRepository.getAllIngredient().map { it.toUIModel() }
                }.await()
            _isOwnedIngredientListLoading.value = false
        }
    }

    suspend fun observeOwnedIngredientList() {
        withContext(Dispatchers.IO) {
            ownedLiqueurRepository.provideAllIngredientFlow().collect {
                _ownedCocktailIngredients.value =
                    it.map { liqueurData -> liqueurData.toUIModel() }
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

    suspend fun fetchAllIngredientsFromAPI() {
        withContext(Dispatchers.IO) {
            _isCocktailIngredientListLoading.value = true
            _isFetchFailed.value = false
            val allIngredients = async {
                cocktailApiRepository.getAllIngredients()
            }.await()
            if (allIngredients.isNotEmpty() && allIngredients.first().fetchFailed) {
                _isFetchFailed.value = true
                _isCocktailIngredientListLoading.value = false
            } else {
                _ingredientList.value = allIngredients.map { it.toUIModel() }
                _isFetchFailed.value = false
                _isCocktailIngredientListLoading.value = false
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