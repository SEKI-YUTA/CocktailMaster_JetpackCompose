package com.example.cocktailmaster.data.interfaces

import com.example.cocktailmaster.data.model.CocktailIngredient_Data
import kotlinx.coroutines.flow.Flow

// Roomデータベースから所有中のリキュール（ジンとか）の情報を拾う
interface OwnedLiqueurRepository {
    suspend fun provideAllIngredient(): Flow<List<CocktailIngredient_Data>>
}