package com.exam.model.binding;

import com.exam.model.entities.Recipe;

import javax.validation.constraints.NotNull;
import java.util.List;

public class MenuAddBindingModel {
    private String name;
    private String recipes;
    private String description;

    public MenuAddBindingModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public String getRecipes() {
        return recipes;
    }

    public void setRecipes(String recipes) {
        this.recipes = recipes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
