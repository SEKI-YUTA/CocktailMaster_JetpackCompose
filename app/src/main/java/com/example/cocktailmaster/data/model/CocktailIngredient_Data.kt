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
    @SerialName("ID")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @SerialName("IsOwned")
    @ColumnInfo(name = "is_owned")
    val isOwned: Boolean = false,

    @SerialName("Name")
    @ColumnInfo(name = "name")
    val name: String,

    @SerialName("Vol")
    @ColumnInfo(name = "vol")
    val vol: Int = 0,

    @SerialName("Description")
    @ColumnInfo(name = "description")
    val description: String = "",

    val fetchFailed: Boolean = false,

) {
    fun toUIModel(): CocktailIngredient_UI {
        return CocktailIngredient_UI(
            id = id,
            name = name,
            isOwned = isOwned,
            vol = vol,
            description = description,
        )
    }
}
