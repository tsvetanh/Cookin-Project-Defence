package com.exam.repository;

import com.exam.model.entities.Recipe;
import com.exam.view.RecipeViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    boolean findByName(String name);

}
