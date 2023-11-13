package com.yuta.cocktailmaster.data.repository

import com.yuta.cocktailmaster.data.ConstantValues
import com.yuta.cocktailmaster.data.api.CocktailApi
import com.yuta.cocktailmaster.data.interfaces.CocktailApiRepository
import com.yuta.cocktailmaster.data.model.CocktailIngredient_Data
import com.yuta.cocktailmaster.data.model.Cocktail_Data
import com.yuta.cocktailmaster.ui.model.CocktailIngredient_UI
import com.yuta.cocktailmaster.ui.model.Cocktail_UI
import java.net.SocketTimeoutException

class CocktailApiRepository_Impl: CocktailApiRepository {
    override suspend fun getAllIngredients(): List<CocktailIngredient_UI> {
        println("getAllIngredients")
        // 失敗したときのメッセージを入れたデータを一旦用意しておく
        var ingredientList = listOf<CocktailIngredient_Data>(
            ConstantValues.failMessageIngredients
        )
        try {
            ingredientList = CocktailApi.retrofitService.getAllIngredients()
        } catch(e: SocketTimeoutException) {
            println("failed xxxxxx")
            println(e)
        }
        println("fetched size: ${ingredientList.size}")
        return ingredientList.map { it.toUIModel() }
    }

    override suspend fun getAllCocktails(): List<Cocktail_UI> {
        val cocktailList = CocktailApi.retrofitService.getAllCocktail()
        return cocktailList.map { it.toUIModel() }
    }

    override suspend fun craftableCocktails(query: List<String>): List<Cocktail_UI> {
        var cocktailList = listOf<Cocktail_Data>(
            ConstantValues.failMessageCocktail
        )
        try {
            cocktailList = CocktailApi.retrofitService.craftableCocktails(query)
        } catch(e: SocketTimeoutException) {
            println("failed xxxxxx")
            println(e)
        }
        return cocktailList.map { it.toUIModel() }
    }
}