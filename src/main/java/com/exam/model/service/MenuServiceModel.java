package com.exam.model.service;

import com.exam.model.entities.Recipe;

import java.util.List;

public class MenuServiceModel {
    private String name;
    private List<Recipe> recipes;
    private String description;

    public MenuServiceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
