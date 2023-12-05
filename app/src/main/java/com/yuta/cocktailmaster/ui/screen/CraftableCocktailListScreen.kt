package com.yuta.cocktailmaster.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yuta.cocktailmaster.FakeRepositoryProvider
import com.yuta.cocktailmaster.LocalApiRepository
import com.yuta.cocktailmaster.R
import com.yuta.cocktailmaster.data.ConstantValues
import com.yuta.cocktailmaster.ui.component.CenterMessage
import com.yuta.cocktailmaster.ui.component.CocktailListItem
import com.yuta.cocktailmaster.ui.component.LoadingMessage
import com.yuta.cocktailmaster.ui.component.MyDropDownMenu
import com.yuta.cocktailmaster.ui.model.Cocktail_UI
import com.yuta.cocktailmaster.ui.theme.CocktailMasterTheme
import com.yuta.cocktailmaster.ui.viewmodels.CraftableCocktailListScreenViewModel
import com.yuta.cocktailmaster.ui.viewmodels.TabItems
import com.yuta.cocktailmaster.util.CocktailMasterPreviewAnnotation

@Composable
fun CraftableCocktailListScreen(
    viewModel: CraftableCocktailListScreenViewModel,
    windowWidthSizeClass: WindowWidthSizeClass
) {
    println(windowWidthSizeClass)
    val viewState = viewModel.viewState.collectAsState().value
    val craftableList by remember(viewState) {
        derivedStateOf {
            viewState.craftableCocktailList
        }
    }
    val allCocktailList by remember(viewState) {
        derivedStateOf {
            viewState.allCocktailList
        }
    }
    val cocktailCategories = listOf("すべて") + ConstantValues.cocktailCategories
    var userSelectCategory by remember { mutableStateOf(cocktailCategories[0]) }
    LaunchedEffect(key1 = true) {
        viewModel.getCocktails()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                Text("絞り込み: ", modifier = Modifier.padding(end = 8.dp))
                MyDropDownMenu(
                    items = cocktailCategories,
                    selectedVal = userSelectCategory
                ) {
                    userSelectCategory = it
                }
            }
            if (windowWidthSizeClass == WindowWidthSizeClass.Expanded) {
                Row {
                    CraftableCocktailScreenContent(
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f),
                        craftableList = craftableList,
                        userSelectCategory = userSelectCategory,
                        isCraftableCocktailFetching = viewState.isCraftableCocktailFetching,
                        isFetchFailed = viewState.isFetchFailed
                    )
                    AllCocktailListScreenContent(
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f),
                        allCocktailList = allCocktailList,
                        userSelectCategory = userSelectCategory,
                        isAllCocktailFetching = viewState.isCraftableCocktailFetching,
                        isFetchFailed = viewState.isFetchFailed
                    )
                }


            } else {
                TabRow(
                    selectedTabIndex = viewState.selectedTab.idx
                ) {
                    CraftableCocktailListScreenViewModel.allTabs.map {
                        Tab(
                            text = { Text(it.title) },
                            selected = viewState.selectedTab == it,
                            onClick = {
                                viewModel.setSelectedTab(it)
                            }
                        )
                    }
                }
                when (viewState.selectedTab) {
                    TabItems.CRAFTABLE -> {
                        CraftableCocktailScreenContent(
                            craftableList = craftableList,
                            userSelectCategory = userSelectCategory,
                            isCraftableCocktailFetching = viewState.isCraftableCocktailFetching,
                            isFetchFailed = viewState.isFetchFailed
                        )
                    }

                    TabItems.ALL_COCKTAILS -> {
                        AllCocktailListScreenContent(
                            allCocktailList = allCocktailList,
                            userSelectCategory = userSelectCategory,
                            isAllCocktailFetching = viewState.isAllCocktailFetching,
                            isFetchFailed = viewState.isFetchFailed
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CraftableCocktailScreenContent(
    modifier: Modifier = Modifier,
    craftableList: List<Cocktail_UI>,
    userSelectCategory: String,
    isCraftableCocktailFetching: Boolean,
    isFetchFailed: Boolean,
) {
    Box(modifier) {
        LazyColumn(modifier) {
            items(
                craftableList,
                key = { it.name + it.cocktailId }
            ) { cocktail_UI ->
                if (userSelectCategory == "すべて" || userSelectCategory == cocktail_UI.category) {
                    CocktailListItem(cocktail_UI = cocktail_UI)
                }
            }
        }
        if (isCraftableCocktailFetching) {
            LoadingMessage()
        } else if (isFetchFailed) {
            CenterMessage(
                message = stringResource(R.string.fetch_failed_message),
                icon = Icons.Default.Refresh,
                iconTapAction = {
                }
            )
        } else if (!isCraftableCocktailFetching && craftableList.isEmpty()) {
            CenterMessage(message = stringResource(R.string.craftable_cocktail_not_found))
        }
    }

}

@Composable
fun AllCocktailListScreenContent(
    modifier: Modifier = Modifier,
    allCocktailList: List<Cocktail_UI>,
    userSelectCategory: String,
    isAllCocktailFetching: Boolean,
    isFetchFailed: Boolean
) {
    Box(modifier) {
        LazyColumn(modifier) {
            items(
                allCocktailList,
                key = { it.name + it.cocktailId }
            ) { cocktail_UI ->
                if (userSelectCategory == "すべて" || userSelectCategory == cocktail_UI.category) {
                    CocktailListItem(cocktail_UI = cocktail_UI)
                }
            }
        }

        if (isAllCocktailFetching) {
            LoadingMessage()
        } else if (isFetchFailed) {
            CenterMessage(
                message = stringResource(R.string.fetch_failed_message),
                icon = Icons.Default.Refresh,
                iconTapAction = {
                }
            )
        }

    }
}

@CocktailMasterPreviewAnnotation
@Composable
fun CraftableCocktailListScreenPreview() {
    FakeRepositoryProvider {
        val fakeApiRepository = LocalApiRepository.current
        val viewModel = CraftableCocktailListScreenViewModel(
            apiRepository = fakeApiRepository,
            ingredientList = emptyList()
        )
        CocktailMasterTheme {
            Surface {
                CraftableCocktailListScreen(
                    viewModel = viewModel,
                    windowWidthSizeClass = WindowWidthSizeClass.Medium
                )
            }
        }
    }
}
