package com.example.cocktailmaster.ui.model

/*
リキュールではないジンなどもこのクラスに分類するのでAlcoholというクラス名にした方がいいかも
抽象的すぎる気がするので今はLiqueurという名前にしている
*/
// 例：ジン、ウォッカ、ラム、テキーラ、ウイスキー、ブランデーなどのカクテルの原料になるもの
data class CocktailIngredient_UI(
    val name: String,
    val imageUri: String?,
    val isOwned: Boolean = false,
    val description: String = "",
)
