package com.exam.repository;

import com.exam.model.entities.Recipe;
import com.exam.view.RecipeViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    boolean findByName(String name);

    Recipe getByName(String name);

    @Query(value = "SELECT * FROM recipes LIMIT 3", nativeQuery = true)
    List<Recipe> getFirstThreeRecipes();
}
