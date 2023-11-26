package com.yuta.cocktailmaster.ui.viewmodels

import com.yuta.cocktailmaster.data.interfaces.CocktailApiRepository
import com.yuta.cocktailmaster.ui.model.CocktailIngredient_UI
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.junit.Test

class AddCocktailIngredientScreenViewModelTest {
    fun AddCocktailIngredientScreenViewModelFactory(
        apiRepository: CocktailApiRepository = mockk()
    ): AddCocktailIngredientScreenViewModel {
        return AddCocktailIngredientScreenViewModel(
            apiRepository = apiRepository
        )
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
    fun `材料を追加するダイアログが表示される`() {
        val viewModel = AddCocktailIngredientScreenViewModelFactory()
        val ingredient = IngredientFactory()

        viewModel.onIngredientTapped(ingredient)
        val viewState = viewModel.viewState.value

        viewState.selectedIngredient shouldBe ingredient
        viewState.isShowingAddDialog shouldBe true
    }

    @Test
    fun `材料を追加するダイアログを閉じれる`() {
        val viewModel = AddCocktailIngredientScreenViewModelFactory()
        val ingredient = IngredientFactory()

        viewModel.onIngredientTapped(ingredient)
        viewModel.onCloseAddDialog()
        val viewState = viewModel.viewState.value

        viewState.selectedIngredient shouldBe null
        viewState.isShowingAddDialog shouldBe false
    }

    @Test
    fun `ユーザーの入力が更新される`() {
        val viewModel = AddCocktailIngredientScreenViewModelFactory()
        val userInput = AddCocktailIngredientScreenViewModel.UserInputState(
            description = "hogehoge"
        )

        viewModel.onUpdateUserInput(userInput)
        val viewState = viewModel.viewState.value

        viewState.userInputState shouldBe userInput
    }

    @Test
    fun `ユーザーの入力をリセットできる`() {
        val viewModel = AddCocktailIngredientScreenViewModelFactory()
        val userInput = AddCocktailIngredientScreenViewModel.UserInputState(
            description = "hogehoge"
        )

        viewModel.onUpdateUserInput(userInput)
        viewModel.onResetUserInput()
        val viewState = viewModel.viewState.value

        viewState.userInputState shouldBe AddCocktailIngredientScreenViewModel.UserInputState()
    }
}
