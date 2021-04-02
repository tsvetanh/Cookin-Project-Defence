package com.exam.service.impl;

import com.exam.model.entities.Recipe;
import com.exam.model.entities.User;
import com.exam.model.service.RecipeServiceModel;
import com.exam.repository.RecipeRepository;
import com.exam.repository.UserRepository;
import com.exam.service.RecipeService;
import com.exam.view.RecipeViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             UserRepository userRepository, ModelMapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createRecipe(RecipeServiceModel serviceModel) {
        Recipe recipe = modelMapper.map(serviceModel, Recipe.class);
        User creator = userRepository.
                findByUsername(serviceModel.getAddedBy().getUsername()).
                orElseThrow(() -> new IllegalArgumentException("Creator " + serviceModel.getAddedBy().getUsername() + " could not be found"));

        recipe.setAddedBy(creator);

        recipeRepository.save(recipe);
    }

    @Override
    public List<RecipeViewModel> getAllRecipes() {
        List<RecipeViewModel> viewModels = new ArrayList<>();

        List<Recipe> recipes = recipeRepository.findAll();
        System.out.println(recipes);

        recipes.forEach(recipe -> {
            RecipeViewModel recipeViewModel = new RecipeViewModel();
            modelMapper.map(recipe, recipeViewModel);
            recipeViewModel.setName(recipe.getName());
            recipeViewModel.setImgUrl(recipe.getImgUrl());
            recipeViewModel.setDifficulty(recipe.getDifficulty());
            recipeViewModel.setAddedBy(recipe.getAddedBy().getUsername());

            viewModels.add(recipeViewModel);
        });

        return viewModels;
    }

    @Override
    public Recipe findEntityById(Long albumId) {
        return null;
    }
}
