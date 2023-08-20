package com.example.cocktailmaster.ui.model

import com.example.cocktailmaster.data.model.CocktailIngredient_Data

/*
リキュールではないジンなどもこのクラスに分類するのでAlcoholというクラス名にした方がいいかも
抽象的すぎる気がするので今はLiqueurという名前にしている
*/
// 例：ジン、ウォッカ、ラム、テキーラ、ウイスキー、ブランデーなどのカクテルの原料になるもの
data class CocktailIngredient_UI(
    val id: Int = 0,
    val name: String,
    val isOwned: Boolean = false,
    val vol: Int = 0,
    val description: String = "",
) {
    fun toDataModel(): CocktailIngredient_Data {
        return CocktailIngredient_Data(
            id = id,
            name = name,
            vol = vol,
            isOwned = isOwned,
            description = description,
        )
    }
}
