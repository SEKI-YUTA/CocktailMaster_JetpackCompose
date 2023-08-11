package com.example.cocktailmaster.data

import com.example.cocktailmaster.data.model.CocktailIngredient_Data

// Roomデータベースから所有中のリキュール（ジンとか）の情報を拾う
interface OwnedLiqueurRepository {
    suspend fun getAllOwnedLiqueur(): List<CocktailIngredient_Data>
}