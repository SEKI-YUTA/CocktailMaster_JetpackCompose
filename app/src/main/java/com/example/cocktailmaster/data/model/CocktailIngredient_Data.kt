package com.example.cocktailmaster.data.model

import com.example.cocktailmaster.ui.model.CocktailIngredient_UI
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
リキュールではないジンなどもこのクラスに分類するのでAlcoholというクラス名にした方がいいかも
抽象的すぎる気がするので今はLiqueurという名前にしている
*/
// 例：ジン、ウォッカ、ラム、テキーラ、ウイスキー、ブランデー、オレンジジュースなどのカクテルの原料になるもの
// データソースから引っ張ってくる時のデータ型そのためUIに表示させる時はtoUIModel()を呼び出してUI用のデータ型に変換する
@Serializable
data class CocktailIngredient_Data(
    @SerialName("ID")
    val id: Int,
    @SerialName("Name")
    val name: String,
    @SerialName("ImageUri")
    val imageUri: String? = null,
    @SerialName("IsOwned")
    val isOwned: Boolean = false,
    @SerialName("Description")
    val description: String = "",
    @SerialName("IsAlcohol")
    val isAlcohol: Boolean = false,
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
