package com.example.cocktailmaster.data

import com.example.cocktailmaster.data.model.CocktailIngredient_Data
import com.example.cocktailmaster.data.model.Cocktail_Data

class DemoData {
    companion object {
        val cocktailList = listOf<Cocktail_Data>(
            Cocktail_Data("ジンバッグ", null, "ジンとジンジャーエールを混ぜたカクテル", true, listOf(
                CocktailIngredient_Data( name ="ジン", description = "スピリッツ", vol = 40),
                CocktailIngredient_Data(name = "ジンジャーエール", description = "ジンジャーエール", vol = 0)
            )),
        )

        val liqueurList = listOf(
            CocktailIngredient_Data( name ="ジン", description = "スピリッツ", vol = 40),
            CocktailIngredient_Data( name ="ウォッカ", description = "スピリッツ", vol = 40),
            CocktailIngredient_Data( name ="ラム", description = "スピリッツ", vol = 40),
            CocktailIngredient_Data( name ="テキーラ", description = "", vol = 40),
            CocktailIngredient_Data( name ="ジンジャーエール", description = "", vol = 0),
            CocktailIngredient_Data( name ="オレンジジュース", description = "", vol = 0),
        )
    }
}