package com.example.cocktailmaster.data.repository

import com.example.cocktailmaster.data.DemoData
import com.example.cocktailmaster.data.interfaces.CocktailApiRepository
import com.example.cocktailmaster.data.model.Cocktail_Data
import com.example.cocktailmaster.data.model.CocktailIngredient_Data

// フェイクリポジトリ
// 全ての材料と作れるカクテルの情報を返す
class CocktailApiRepository_FakeImpl: CocktailApiRepository {
    override suspend fun getAllIngredients(): List<CocktailIngredient_Data> {
        return DemoData.liqueurList
    }

    override suspend fun craftableCocktails(query: List<String>): List<Cocktail_Data> {
        return DemoData.cocktailList
    }
}