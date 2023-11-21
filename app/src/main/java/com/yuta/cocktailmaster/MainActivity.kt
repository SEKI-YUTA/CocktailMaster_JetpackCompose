package com.yuta.cocktailmaster

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yuta.cocktailmaster.data.AppContainer
import com.yuta.cocktailmaster.data.repository.AppStatusRepository_Impl
import com.yuta.cocktailmaster.ui.theme.CocktailMasterTheme
import com.yuta.cocktailmaster.ui.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_status")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer: AppContainer = (application as CocktailMasterApplication).appContainer
        setContent {
            RepositoryProvider {
                val ownedIngredientRepository = LocalOwnedIngredientRepository.current
                val mainViewModel = viewModel<MainViewModel>(
                    factory = MainViewModel.provideFactory(
                        ownedIngredientRepository = ownedIngredientRepository,
                    )
                )
                val mainViewState = mainViewModel.viewState.collectAsState().value
                LaunchedEffect(key1 = true) {
                    mainViewModel.readIsOnboardingFinished(this@MainActivity)
                }
                CocktailMasterTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MainHost(
                            appContainer = appContainer,
                            mainViewState = mainViewState,
                            networkConnectionStateChanged = {
                                mainViewModel.setIsNetworkConnected(it)
                            },
                            onAddOwnedIngredient = {
                                lifecycleScope.launch(Dispatchers.IO) {
                                    ownedIngredientRepository.addOwnedIngredient(it)
                                }
                            },
                            onEditOwnedIngredient = {
                                lifecycleScope.launch(Dispatchers.IO) {
                                    ownedIngredientRepository.editOwnedIngredient(it)
                                }
                            },
                            onDeleteOwnedIngredient = {
                                lifecycleScope.launch(Dispatchers.IO) {
                                    ownedIngredientRepository.deleteIngredient(it)
                                }
                            },
                            onUpdateOnboardingFinished = {
                                println("onUpdateOnboardingFinished: $it")
                                lifecycleScope.launch(Dispatchers.IO) {
                                    AppStatusRepository_Impl().writeIsOnboardingFinished(
                                        this@MainActivity,
                                        it
                                    )
                                }
                                mainViewModel.setIsOnboardingFinished(this@MainActivity, it)
                            }
                        )
                    }
                }
            }
        }
    }
}
