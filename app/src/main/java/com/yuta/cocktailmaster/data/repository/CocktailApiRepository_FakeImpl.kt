package com.yuta.cocktailmaster.data.repository

import com.yuta.cocktailmaster.data.DemoData
import com.yuta.cocktailmaster.data.interfaces.CocktailApiRepository
import com.yuta.cocktailmaster.data.model.Cocktail_Data
import com.yuta.cocktailmaster.data.model.CocktailIngredient_Data

// フェイクリポジトリ
// 全ての材料と作れるカクテルの情報を返す
class CocktailApiRepository_FakeImpl: CocktailApiRepository {
    override suspend fun getAllIngredients(): List<CocktailIngredient_Data> {
        return DemoData.ingredientList
    }

    override suspend fun craftableCocktails(query: List<String>): List<Cocktail_Data> {
        return DemoData.cocktailList
    }
}