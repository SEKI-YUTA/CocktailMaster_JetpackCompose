package com.example.cocktailmaster.data

import com.example.cocktailmaster.data.model.CocktailIngredient_Data
import kotlinx.coroutines.delay

class OwnedLiqueurRepository_Impl: OwnedLiqueurRepository {
    override suspend fun getAllOwnedLiqueur(): List<CocktailIngredient_Data> {
        // 本番はここでデータベースからデータを引っ張ってくる処理をする
        delay(500)
        return DemoData.liqueurList
    }
}