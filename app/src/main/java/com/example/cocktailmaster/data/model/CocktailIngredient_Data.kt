package com.example.cocktailmaster.data.model

import com.example.cocktailmaster.ui.model.CocktailIngredient_UI

/*
リキュールではないジンなどもこのクラスに分類するのでAlcoholというクラス名にした方がいいかも
抽象的すぎる気がするので今はLiqueurという名前にしている
*/
// 例：ジン、ウォッカ、ラム、テキーラ、ウイスキー、ブランデー、オレンジジュースなどのカクテルの原料になるもの
// データソースから引っ張ってくる時のデータ型そのためUIに表示させる時はtoUIModel()を呼び出してUI用のデータ型に変換する
data class CocktailIngredient_Data(
    val name: String,
    val imageUri: String?,
    val isOwned: Boolean = false,
    val description: String?,
) {
    fun toUIModel(): CocktailIngredient_UI {
        return CocktailIngredient_UI(
            name = name,
            imageUri = imageUri,
            isOwned = isOwned,
            description = description,
        )
    }
}
