package com.yuta.cocktailmaster.ui.model

import com.yuta.cocktailmaster.data.model.CocktailIngredient_Data

/*
リキュールではないジンなどもこのクラスに分類するのでAlcoholというクラス名にした方がいいかも
*/
// 例：ジン、ウォッカ、ラム、テキーラ、ウイスキー、ブランデーなどのカクテルの原料になるもの
data class CocktailIngredient_UI(
    var id: Int = 0,
    val shortName: String,
    val longName: String,
    val description: String = "",
    val vol: Int = 0,
    val ingredientCategoryId: Int = 0,
    val category: String = "",
) {
    fun toDataModel(): CocktailIngredient_Data {
        return CocktailIngredient_Data(
            id = id,
            shortName = shortName,
            longName = longName,
            description = description,
            vol = vol,
            ingredientCategoryId = ingredientCategoryId,
            category = category,
        )
    }
}
