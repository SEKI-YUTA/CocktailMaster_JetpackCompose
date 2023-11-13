package com.yuta.cocktailmaster.data.interfaces

import com.yuta.cocktailmaster.data.model.Cocktail_Data
import com.yuta.cocktailmaster.data.model.CocktailIngredient_Data

/*
インターフェース名からしてカクテルしか返さなさそうだけど実際はリキュールの情報も返す
名前を変えたほうがいいかも
 */
interface CocktailApiRepository {
    suspend fun getAllIngredients(): List<CocktailIngredient_Data>
    // こんな感じでパラメータを渡す（使える素材)
    // ingredients[]=ジン&ingredients[]=ジンジャーエール&ingredients[]=ラム
    suspend fun getAllCocktails(): List<Cocktail_Data>
    suspend fun craftableCocktails(query: List<String>): List<Cocktail_Data>
}