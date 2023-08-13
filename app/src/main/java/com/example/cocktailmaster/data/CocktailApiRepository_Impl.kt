package com.example.cocktailmaster.data

import com.example.cocktailmaster.data.model.CocktailIngredient_Data
import com.example.cocktailmaster.data.model.Cocktail_Data

class CocktailApiRepository_Impl: CocktailApiRepository {
    override suspend fun getAllIngredients(): List<CocktailIngredient_Data> {
        println("getAllIngredients")
        val ingredientList = CocktailApi.retrofitService.getAllIngredients()
        println("fetched size: ${ingredientList.size}")
        return ingredientList
    }

    override suspend fun craftableCocktails(): List<Cocktail_Data> {
        println("craftableCocktails")
        return listOf()
    }
}