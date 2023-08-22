package com.example.cocktailmaster.data.repository

import com.example.cocktailmaster.data.api.CocktailApi
import com.example.cocktailmaster.data.interfaces.CocktailApiRepository
import com.example.cocktailmaster.data.model.CocktailIngredient_Data
import com.example.cocktailmaster.data.model.Cocktail_Data

class CocktailApiRepository_Impl: CocktailApiRepository {
    override suspend fun getAllIngredients(): List<CocktailIngredient_Data> {
        println("getAllIngredients")
        val ingredientList = CocktailApi.retrofitService.getAllIngredients()
        println("fetched size: ${ingredientList.size}")
        return ingredientList
    }

    override suspend fun craftableCocktails(query: List<String>): List<Cocktail_Data> {
        val cocktailList = CocktailApi.retrofitService.craftableCocktails(query)
        return cocktailList
    }
}