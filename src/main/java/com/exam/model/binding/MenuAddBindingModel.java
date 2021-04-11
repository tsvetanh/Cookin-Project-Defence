package com.exam.model.binding;

import com.exam.model.entities.Recipe;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class MenuAddBindingModel {
    private String name;
    private String recipes;
    private String description;

    public MenuAddBindingModel() {
    }

    @NotBlank(message = "Name cannot be empty string")
    @Size(min = 5, max = 20, message = "Name must be between 5 and 20 characters")
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

    @NotBlank(message = "Description cannot be empty string")
    @Size(min = 5, message = "Description must be more than 5 characters")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
