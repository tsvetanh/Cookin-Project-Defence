package com.exam.repository;

import com.exam.model.entities.Recipe;
import com.exam.model.entities.emuns.Difficulty;
import com.exam.view.RecipeViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    boolean findByName(String name);

    Recipe getByName(String name);

    @Query(value = "SELECT * FROM recipes LIMIT 3", nativeQuery = true)
    List<Recipe> getFirstThreeRecipes();

    @Modifying
    @Transactional
    @Query("update Recipe r set r.name = :name, r.imgUrl = :imgUrl, r.difficulty = :difficulty, r.description = :description where r.id = :id")
    void updateRecipe(@Param("id") Long id, @Param("name") String name, @Param("imgUrl") String imgUrl, @Param("difficulty") Difficulty difficulty, @Param("description") String description);
}
