package com.yuta.cocktailmaster

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.yuta.cocktailmaster.data.interfaces.CocktailApiRepository
import com.yuta.cocktailmaster.data.interfaces.OwnedIngredientRepository
import com.yuta.cocktailmaster.data.repository.CocktailApiRepository_FakeImpl
import com.yuta.cocktailmaster.data.repository.CocktailApolloRepository_Impl
import com.yuta.cocktailmaster.data.repository.OwnedIngredientRepository_FakeImpl
import com.yuta.cocktailmaster.data.repository.OwnedIngredientRepository_Impl

val LocalApiRepository = compositionLocalOf<CocktailApiRepository> {
    error("No ApiRepository provided")
}
val LocalOwnedIngredientRepository = compositionLocalOf<OwnedIngredientRepository> {
    error("No OwnedIngredientRepository provided")
}

@Composable
fun RepositoryProvider(
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    CompositionLocalProvider(
        LocalApiRepository provides CocktailApolloRepository_Impl(),
        LocalOwnedIngredientRepository provides OwnedIngredientRepository_Impl(context = context)
    ) {
        content()
    }
}

@Composable
fun FakeRepositoryProvider(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalApiRepository provides CocktailApiRepository_FakeImpl(),
        LocalOwnedIngredientRepository provides OwnedIngredientRepository_FakeImpl()
    ) {
        content()
    }
}