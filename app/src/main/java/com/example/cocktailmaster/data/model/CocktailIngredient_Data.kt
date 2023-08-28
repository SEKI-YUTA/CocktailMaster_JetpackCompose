package com.example.cocktailmaster.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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
@Entity(tableName = "owned_ingredient")
data class CocktailIngredient_Data(
    @SerialName("ingredient_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @SerialName("short_name")
    @ColumnInfo(name = "short_name")
    val shortName: String,

    @SerialName("long_name")
    @ColumnInfo(name = "long_name")
    val longName: String,

    @SerialName("description")
    @ColumnInfo(name = "description")
    val description: String = "",

    @SerialName("vol")
    @ColumnInfo(name = "vol")
    val vol: Int = 0,

    @SerialName("ingredient_category_id")
    @ColumnInfo(name = "ingredient_category_id")
    val ingredientCategoryId: Int = 0,

    @SerialName("category")
    @ColumnInfo(name = "category")
    val category: String = "",

    val fetchFailed: Boolean = false,

    ) {
    fun toUIModel(): CocktailIngredient_UI {
        return CocktailIngredient_UI(
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
