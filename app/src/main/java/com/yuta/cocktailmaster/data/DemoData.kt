package com.yuta.cocktailmaster.data

import com.yuta.cocktailmaster.data.model.CocktailIngredient_Data
import com.yuta.cocktailmaster.data.model.Cocktail_Data

class DemoData {
    companion object {
        val cocktailList = listOf<Cocktail_Data>(
            Cocktail_Data(
                name = "ジンバッグ", description = "ジンとジンジャーエールを混ぜたカクテル",
                ingredients = listOf(
                    CocktailIngredient_Data(shortName = "ジン", longName = "ドライ・ジン", description = "スピリッツ", vol = 40),
                    CocktailIngredient_Data(shortName = "ジンジャーエール", longName = "ジンジャーエール", description = "ジンジャーエール", vol = 0)
                )
            )
        )

        val ingredientList = listOf(
            CocktailIngredient_Data(shortName = "ジン", longName = "ドライ・ジン", description = "スピリッツ", vol = 40),
            CocktailIngredient_Data(shortName = "ウォッカ", longName = "ウォッカ", description = "スピリッツ", vol = 40),
            CocktailIngredient_Data(shortName = "ラム", longName = "ホワイトラム", description = "スピリッツ", vol = 40),
            CocktailIngredient_Data(shortName = "テキーラ", longName = "テキーラ", description = "", vol = 40),
            CocktailIngredient_Data(shortName = "ジンジャーエール", longName = "ジンジャーエール", description = "", vol = 0),
            CocktailIngredient_Data(shortName = "オレンジジュース", longName = "オレンジジュース", description = "", vol = 0)
        )
    }
}
