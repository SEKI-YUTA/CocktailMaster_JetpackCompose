package com.example.cocktailmaster.data.interfaces

import com.example.cocktailmaster.data.model.CocktailIngredient_Data
import kotlinx.coroutines.flow.Flow

// Roomデータベースから所有中のリキュール（ジンとか）の情報を拾う
interface OwnedIngredientRepository {
    suspend fun provideAllIngredientFlow(): Flow<List<CocktailIngredient_Data>>
    suspend fun getAllIngredient(): List<CocktailIngredient_Data>

    suspend fun deleteIngredient(ingredient: CocktailIngredient_Data)
}