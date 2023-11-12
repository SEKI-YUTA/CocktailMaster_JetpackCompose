package com.yuta.cocktailmaster.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.yuta.cocktailmaster.IngredientListQuery
import com.yuta.cocktailmaster.data.interfaces.CocktailApiRepository
import com.yuta.cocktailmaster.ui.model.CocktailIngredient_UI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddCocktailIngredientScreenViewModel(
    val apiRepository: CocktailApiRepository,
): ViewModel() {
    private val _viewState = MutableStateFlow(AddCocktailIngredientScreenViewState.INITIAL)
    val viewState = _viewState.asStateFlow()

    init {
        if(lastViewState != null) {
            _viewState.value = lastViewState!!
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                fetchAllIngredientsFromAPI()
                testRunGQL()
                lastViewState = _viewState.value
            }
        }
    }
    companion object {
        var lastViewState: AddCocktailIngredientScreenViewState? = null

        fun provideFactory(
            apiRepository: CocktailApiRepository
        ): ViewModelProvider.Factory {
            return object: ViewModelProvider.Factory {
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
        _viewState.value = _viewState.value.copy(
            isLoading = true,
        )
        val tmp = apiRepository.getAllIngredients().map { it.toUIModel() }
        _viewState.value = _viewState.value.copy(
            isLoading = false,
            ingredientList = tmp,
        )
    }

    suspend fun testRunGQL() {
        val appolloClient = ApolloClient.Builder()
            .serverUrl("http://10.0.2.2:4000/graphql")
            .build()
        val response = appolloClient.query(IngredientListQuery()).execute()
        Log.d("GQL ingredientList", "${response.data}")
    }

    fun onIngredientTapped(ingredient: CocktailIngredient_UI) {
        _viewState.value = _viewState.value.copy(
            selectedIngredient = ingredient,
            isShowingAddDialog = true,
        )
    }

    fun onCloseAddDialog() {
        _viewState.value = _viewState.value.copy(
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