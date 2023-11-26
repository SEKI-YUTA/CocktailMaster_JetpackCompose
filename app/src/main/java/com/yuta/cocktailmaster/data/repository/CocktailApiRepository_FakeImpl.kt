package com.yuta.cocktailmaster.data.repository

import com.yuta.cocktailmaster.data.DemoData
import com.yuta.cocktailmaster.data.interfaces.CocktailApiRepository
import com.yuta.cocktailmaster.ui.model.CocktailIngredient_UI
import com.yuta.cocktailmaster.ui.model.Cocktail_UI

// フェイクリポジトリ
// 全ての材料と作れるカクテルの情報を返す
class CocktailApiRepository_FakeImpl : CocktailApiRepository {
    override suspend fun getAllIngredients(): List<CocktailIngredient_UI> {
        return DemoData.ingredientList.map { it.toUIModel() }
    }

    override suspend fun getAllCocktails(): List<Cocktail_UI> {
        return DemoData.cocktailList.map { it.toUIModel() }
    }

    override suspend fun craftableCocktails(query: List<String>): List<Cocktail_UI> {
        return DemoData.cocktailList.map { it.toUIModel() }
    }
}
