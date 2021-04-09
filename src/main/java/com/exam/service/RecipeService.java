package com.exam.service;

import com.exam.model.entities.Recipe;
import com.exam.model.service.RecipeServiceModel;
import com.exam.view.RecipeViewModel;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    void createRecipe(RecipeServiceModel serviceModel);

    RecipeViewModel findById(Long id);

    Recipe findEntityById(Long albumId);

    List<RecipeViewModel> getAllRecipes();

    RecipeViewModel getRecipeByName(String name);

    Recipe getRecipeEntityByName(String name);

    List<RecipeViewModel> getTop3Recipes();
}
