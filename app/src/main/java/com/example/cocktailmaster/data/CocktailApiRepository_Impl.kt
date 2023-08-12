package com.example.cocktailmaster.data

import com.example.cocktailmaster.data.model.Cocktail_Data
import com.example.cocktailmaster.data.model.CocktailIngredient_Data
import kotlinx.coroutines.delay

class CocktailApiRepository_Impl: CocktailApiRepository {
    override suspend fun getAllIngredients(): List<CocktailIngredient_Data> {
        delay(500)
        return DemoData.liqueurList
    }

    override suspend fun craftableCocktails(): List<Cocktail_Data> {
        delay(500)
        return DemoData.cocktailList
    }
}