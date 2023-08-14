package com.example.cocktailmaster.data.repository

import com.example.cocktailmaster.data.DemoData
import com.example.cocktailmaster.data.interfaces.OwnedLiqueurRepository
import com.example.cocktailmaster.data.model.CocktailIngredient_Data
import kotlinx.coroutines.delay


class OwnedLiqueurRepository_FakeImpl: OwnedLiqueurRepository {
    override suspend fun getAllOwnedLiqueur(): List<CocktailIngredient_Data> {
        // 本番はここでデータベースからデータを引っ張ってくる処理をする
        delay(500)
        return DemoData.liqueurList
    }
}