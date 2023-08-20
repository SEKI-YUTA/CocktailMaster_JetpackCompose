package com.example.cocktailmaster.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cocktailmaster.CocktailMasterApplication
import com.example.cocktailmaster.data.db.AppDatabase
import com.example.cocktailmaster.data.interfaces.CocktailApiRepository
import com.example.cocktailmaster.data.repository.CocktailApiRepository_Impl
import com.example.cocktailmaster.data.interfaces.OwnedLiqueurRepository
import com.example.cocktailmaster.data.repository.OwnedLiqueurRepository_FakeImpl
import com.example.cocktailmaster.data.repository.OwnedLiqueurRepository_Impl
import com.example.cocktailmaster.ui.model.CocktailIngredient_UI
import com.example.cocktailmaster.ui.model.Cocktail_UI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val context: Context,
    private val cocktailApiRepository: CocktailApiRepository,
    // ローカルデータの操作を後でリポジトリに引越しする予定
    private val ownedLiqueurRepository: OwnedLiqueurRepository,
) : ViewModel() {

    init {
        viewModelScope.launch {
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

    private val _currentScreen = MutableStateFlow(Screen.TopScreen)
    val currentScreen = _currentScreen.asStateFlow()

    private val _ownedCocktailIngredients =
        MutableStateFlow<List<CocktailIngredient_UI>>(emptyList())
    val ownedCocktailIngredients = _ownedCocktailIngredients.asStateFlow()

    private val _ingredientList = MutableStateFlow<List<CocktailIngredient_UI>>(emptyList())
    val ingredientList = _ingredientList.asStateFlow()

    private val _craftableCocktailList = MutableStateFlow<List<Cocktail_UI>>(emptyList())
    val craftableCocktailList = _craftableCocktailList.asStateFlow()

    fun setCurrentScreen(screen: Screen) {
        _currentScreen.value = screen
    }

    fun readOwnedIngredientList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
//                val ownedLiqueurList = ownedLiqueurRepository.getAllOwnedLiqueur()
//                    .map { liqueurData -> liqueurData.toUIModel() }
//                _ownedCocktailIngredients.value = ownedLiqueurList
                ownedLiqueurRepository.provideAllIngredient().collect() {
                    _ownedCocktailIngredients.value = it.map { liqueurData -> liqueurData.toUIModel() }
                }
            }
        }
    }

    fun addOwnedIngredient(ingredient: CocktailIngredient_UI) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                AppDatabase.getDatabase(context).ownedIngredientDao().insertIngredient(ingredient.toDataModel())
            }
        }
    }

    fun fetchAllIngredientsFromAPI() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val allIngredients = cocktailApiRepository.getAllIngredients()
                    .map { ingredientData -> ingredientData.toUIModel() }
                _ingredientList.value = allIngredients
            }
        }
    }

    fun findCraftableCocktail() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val query = ownedCocktailIngredients.value.map { it.name }
                val tmpList = cocktailApiRepository.craftableCocktails(query).map { it.toUIModel() }
                _craftableCocktailList.value = tmpList
            }
        }

    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val tmp: Context =
                    (this[APPLICATION_KEY] as CocktailMasterApplication).appContainer.context
//                val cocktailApiRepository = CocktailApiRepository_FakeImpl()
                val cocktailApiRepository = CocktailApiRepository_Impl()
                val ownedLiqueurRepository = OwnedLiqueurRepository_Impl(tmp)
                MainViewModel(
                    tmp,
                    cocktailApiRepository = cocktailApiRepository,
                    ownedLiqueurRepository = ownedLiqueurRepository
                )
            }
        }
    }
}