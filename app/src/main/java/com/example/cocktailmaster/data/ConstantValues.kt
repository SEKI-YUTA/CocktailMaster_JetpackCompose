package com.example.cocktailmaster.data

import com.example.cocktailmaster.data.model.CocktailIngredient_Data
import com.example.cocktailmaster.data.model.Cocktail_Data

class ConstantValues {
    companion object {
        // 材料の取得に失敗したときに１番目の要素に入れるためのデータ
        val failMessageIngredients =
            CocktailIngredient_Data(
                id = -100,
                name = "failed",
                isOwned = false,
                vol = 0,
                description = "",
                fetchFailed = true,
            )

        // カクテルの取得に失敗したときに１番目の要素に入れるためのデータ
        val failMessageCocktail =
            Cocktail_Data(
                name = "failed",
                imageUri = null,
                description = "",
                craftable = false,
                ingredients = emptyList(),
                fetchFailed = true,
            )
    }
}