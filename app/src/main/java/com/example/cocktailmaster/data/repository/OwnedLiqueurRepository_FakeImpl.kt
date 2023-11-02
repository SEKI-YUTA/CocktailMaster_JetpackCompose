package com.example.cocktailmaster.data.repository

import androidx.compose.runtime.snapshotFlow
import com.example.cocktailmaster.data.DemoData
import com.example.cocktailmaster.data.interfaces.OwnedLiqueurRepository
import com.example.cocktailmaster.data.model.CocktailIngredient_Data
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow


class OwnedLiqueurRepository_FakeImpl: OwnedLiqueurRepository {
    override suspend fun provideAllIngredientFlow(): Flow<List<CocktailIngredient_Data>> {
        // 本番はここでデータベースからデータを引っ張ってくる処理をする
        delay(500)
        return snapshotFlow {
            DemoData.liqueurList
        }
    }

    override suspend fun getAllIngredient(): List<CocktailIngredient_Data> {
        // 本番はここでデータベースからデータを引っ張ってくる処理をする
        delay(500)
        return DemoData.liqueurList
    }

    override suspend fun deleteIngredient(ingredient: CocktailIngredient_Data) {
        TODO("Not yet implemented")
    }
}