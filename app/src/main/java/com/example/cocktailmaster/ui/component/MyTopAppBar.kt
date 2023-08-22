@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cocktailmaster.ui.component

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cocktailmaster.ui.theme.CocktailMasterTheme

@Composable
fun MyTopAppBar(navController: NavHostController) {
    val showBackButton = remember { mutableStateOf(false) }
    navController.addOnDestinationChangedListener() { controller, destination, arguments ->
        showBackButton.value = controller.previousBackStackEntry != null
    }
    TopAppBar(
        navigationIcon = {
            if(showBackButton.value) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        },
        title = {},
        actions = {
            IconButton(
                onClick = {
                    // 設定画面へ遷移
                }
            ) {
                Icon(Icons.Default.Settings, contentDescription = null)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MyAppBarPreview_Light() {
    CocktailMasterTheme {
        Surface {
            MyTopAppBar(navController = rememberNavController())
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MyAppBarPreview_Night() {
    CocktailMasterTheme {
        Surface {
            MyTopAppBar(navController = rememberNavController())
        }
    }
}