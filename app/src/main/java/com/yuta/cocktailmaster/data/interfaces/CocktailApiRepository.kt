package com.yuta.cocktailmaster.data.interfaces

import com.yuta.cocktailmaster.ui.model.CocktailIngredient_UI
import com.yuta.cocktailmaster.ui.model.Cocktail_UI

/*
インターフェース名からしてカクテルしか返さなさそうだけど実際はリキュールの情報も返す
名前を変えたほうがいいかも
 */
interface CocktailApiRepository {
    suspend fun getAllIngredients(): List<CocktailIngredient_UI>

    // こんな感じでパラメータを渡す（使える素材)
    // ingredients[]=ジン&ingredients[]=ジンジャーエール&ingredients[]=ラム
    suspend fun getAllCocktails(): List<Cocktail_UI>
    suspend fun craftableCocktails(query: List<String>): List<Cocktail_UI>
}