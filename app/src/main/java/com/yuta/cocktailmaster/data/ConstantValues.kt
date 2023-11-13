package com.yuta.cocktailmaster.data

import com.yuta.cocktailmaster.data.model.CocktailIngredient_Data
import com.yuta.cocktailmaster.data.model.Cocktail_Data

class ConstantValues {
    companion object {
        val ingredientCategories = listOf(
            "スピリッツ",
            "リキュール",
            "ソフトドリンク",
            "ウィスキー",
            "ブランデー",
            "焼酎",
            "日本酒"
        )

        val cocktailCategories = listOf(
            "ショートカクテル",
            "ロングカクテル",
            "ノンアルコール"
        )

        // 材料の取得に失敗したときに１番目の要素に入れるためのデータ
        val failMessageIngredients =
            CocktailIngredient_Data(
                id = -100,
                shortName = "failed",
                longName = "failed",
                vol = 0,
                description = "",
                fetchFailed = true,
            )

        // カクテルの取得に失敗したときに１番目の要素に入れるためのデータ
        val failMessageCocktail =
            Cocktail_Data(
                name = "failed",
                description = "",
                ingredients = emptyList(),
                fetchFailed = true,
            )
    }
}