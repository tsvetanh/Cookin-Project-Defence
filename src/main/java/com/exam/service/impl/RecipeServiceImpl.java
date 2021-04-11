package com.exam.service.impl;

import com.exam.model.entities.Recipe;
import com.exam.model.entities.User;
import com.exam.model.service.ProductServiceModel;
import com.exam.model.service.RecipeServiceModel;
import com.exam.repository.RecipeRepository;
import com.exam.repository.UserRepository;
import com.exam.service.RecipeService;
import com.exam.view.RecipeViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void createRecipe(RecipeServiceModel recipeServiceModel) throws IllegalArgumentException {
        Recipe recipe = modelMapper.map(recipeServiceModel, Recipe.class);
        User creator = userRepository.
                findByUsername(recipeServiceModel.getAddedBy()).
                orElseThrow(() -> new IllegalArgumentException("Creator " + recipeServiceModel.getAddedBy() + " could not be found"));

        recipe.setAddedBy(recipeServiceModel.getAddedBy());

        recipeRepository.save(recipe);
    }

    @Override
    public List<RecipeViewModel> getAllRecipes() {
        List<RecipeViewModel> viewModels = new ArrayList<>();

        List<Recipe> recipes = recipeRepository.findAll();

        recipes.forEach(recipe -> {
            RecipeViewModel recipeViewModel = modelMapper.map(recipe, RecipeViewModel.class);
            viewModels.add(recipeViewModel);
        });


        return viewModels;
    }

    @Override
    public RecipeViewModel getRecipeByName(String name) {

        Recipe recipe = recipeRepository.getByName(name);


        List<Recipe> recipes = recipeRepository.findAll();

        RecipeViewModel recipeViewModel = new RecipeViewModel();

        modelMapper.map(recipe, recipeViewModel);

        return recipeViewModel;
    }

    @Override
    public Recipe getRecipeEntityByName(String name) {
        return recipeRepository.getByName(name);
    }

    @Override
    public List<RecipeViewModel> getTop3Recipes() {
        List<Recipe> recipes =  recipeRepository.getFirstThreeRecipes();

        List<RecipeViewModel> viewModels = new ArrayList<>();

        recipes.forEach(recipe -> {
            RecipeViewModel recipeViewModel = modelMapper.map(recipe, RecipeViewModel.class);
            viewModels.add(recipeViewModel);
        });

        return viewModels;
    }

    @Override
    public void updateRecipe(RecipeServiceModel r) {
            recipeRepository.updateRecipe(r.getId(), r.getName(), r.getImgUrl(), r.getDifficulty(), r.getDescription());
    }



    @Override
    public Recipe findEntityById(Long recipeId) {
        return null;
    }


    @Override
    public RecipeViewModel findById(Long recipeId) {
        return recipeRepository
                .findById(recipeId)
                .map(recipeEntity -> {
                    return modelMapper
                            .map(recipeEntity, RecipeViewModel.class);
                })
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}


