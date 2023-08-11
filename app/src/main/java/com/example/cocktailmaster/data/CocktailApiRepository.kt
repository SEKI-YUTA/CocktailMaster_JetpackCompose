package com.example.cocktailmaster.data

import com.example.cocktailmaster.data.model.Cocktail_Data
import com.example.cocktailmaster.data.model.CocktailIngredient_Data

/*
インターフェース名からしてカクテルしか返さなさそうだけど実際はリキュールの情報も返す
名前を変えたほうがいいかも
 */
interface CocktailApiRepository {
    suspend fun getAllLiqueur(): List<CocktailIngredient_Data>
    suspend fun craftableCocktails(): List<Cocktail_Data>
}