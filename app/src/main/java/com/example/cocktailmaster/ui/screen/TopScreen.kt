package com.example.cocktailmaster.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cocktailmaster.ui.MainViewModel
import com.example.cocktailmaster.ui.component.MenuButton

/*
所有中の酒をリスト表示して
追加画面と現在所有中の酒で作ることができるカクテルを表示する画面へ遷移できる
*/
@Composable
fun TopScreen(navController: NavHostController, viewModel: MainViewModel) {
    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MenuButton(text = "カクテル一覧へ", icon = Icons.Default.List) {
                    println("カクテル一覧へ")
//                    navController.navigate(Screen.CraftableCocktailListScreen.name)
                }
                MenuButton(text = "追加画面へ", icon = Icons.Default.Add) {
                    println("追加画面へ")
//                   navController.navigate(Screen.AddCocktailIngredientScreen.name)
                }
            }
            Text("所有中の材料",
                style = TextStyle(fontSize = 22.sp),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}