package com.exam.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

    @OneToMany
    public List<Recipe> getDishes() {
        return recipes;
    }

    public void setDishes(List<Recipe> recipes) {
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
