package com.exam.model.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "menues")
public class Menu extends BaseEntity {
    private String name;
    private List<Recipe> recipes;
    private String description;

    public Menu() {
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.EAGER)
    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Column(name = "description", columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

