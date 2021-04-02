package com.exam.service;

import com.exam.model.entities.Recipe;
import com.exam.model.service.RecipeServiceModel;
import com.exam.view.RecipeViewModel;

import java.util.List;

public interface RecipeService {
    void createRecipe(RecipeServiceModel serviceModel);

//    RecipeViewModel findById(Long id);

    Recipe findEntityById(Long albumId);

    List<RecipeViewModel> getAllRecipes();

}
