package com.yuta.cocktailmaster.ui.viewmodels

import com.yuta.cocktailmaster.ui.model.CocktailIngredient_UI
import io.kotest.matchers.shouldBe
import org.junit.Test

class TopScreenViewModelTest {

    fun TopScreenViewModelFactory(): TopScreenViewModel {
        return TopScreenViewModel()
    }
    fun IngredientFactory(
        id: Int = 0,
        shortName: String = "ingredient_shortname",
        longName: String = "ingredient_longname",
        description: String = "ingredient_description",
        vol: Int = 0,
        ingredientCategoryId: Int = 0,
        category: String = "category_name"
    ): CocktailIngredient_UI {
        return CocktailIngredient_UI(
            id = id,
            shortName = shortName,
            longName = longName,
            description = description,
            vol = vol,
            ingredientCategoryId = ingredientCategoryId,
            category = category
        )
    }
    @Test
    fun `材料を削除しようとした際にダイアログが表示される`() {
        val viewModel = TopScreenViewModelFactory()
        val ingredient = IngredientFactory()

        viewModel.onIngredientDeleteRequest(ingredient)
        val viewState = viewModel.viewState.value

        viewState.isShowingDeleteConfirmDialog shouldBe true
        viewState.selectedIngredient shouldBe ingredient
    }

    @Test
    fun `材料を削除するダイアログを閉じれる`() {
        val viewModel = TopScreenViewModelFactory()
        val ingredient = IngredientFactory()

        viewModel.onIngredientDeleteRequest(ingredient)
        viewModel.onCloseDeleteConfirmDialog()
        val viewState = viewModel.viewState.value

        viewState.isShowingDeleteConfirmDialog shouldBe false
        viewState.selectedIngredient shouldBe null
    }

    @Test
    fun `材料を編集するダイアログが表示される`() {
        val viewModel = TopScreenViewModelFactory()
        val ingredient = IngredientFactory()

        viewModel.onIngredientEditRequest(ingredient)
        val viewState = viewModel.viewState.value

        viewState.isShowingEditDialog shouldBe true
        viewState.selectedIngredient shouldBe ingredient
    }

    @Test
    fun `材料を編集するダイアログを閉じれる`() {
        val viewModel = TopScreenViewModelFactory()
        val ingredient = IngredientFactory()

        viewModel.onIngredientEditRequest(ingredient)
        viewModel.onCloseEditDialog()
        val viewState = viewModel.viewState.value

        viewState.isShowingEditDialog shouldBe false
        viewState.selectedIngredient shouldBe null
    }

    @Test
    fun `ユーザーの入力が更新される`() {
        val viewModel = TopScreenViewModelFactory()
        val userInput = AddCocktailIngredientScreenViewModel.UserInputState(
            description = "hogehoge"
        )

        viewModel.onUpdateUserInput(userInput)
        val viewState = viewModel.viewState.value

        viewState.userInputState shouldBe userInput
    }

    @Test
    fun `ユーザーの入力をリセットできる`() {
        val viewModel = TopScreenViewModelFactory()
        val userInput = AddCocktailIngredientScreenViewModel.UserInputState(
            description = "hogehoge"
        )

        viewModel.onUpdateUserInput(userInput)
        viewModel.onResetUserInput()
        val viewState = viewModel.viewState.value

        viewState.userInputState shouldBe AddCocktailIngredientScreenViewModel.UserInputState()
    }
}