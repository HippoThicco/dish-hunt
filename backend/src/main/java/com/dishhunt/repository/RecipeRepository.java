package com.dishhunt.repository;

import com.dishhunt.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByAuthorIdOrderByCreatedAtDesc(Long authorId);

    @Query("SELECT r FROM Recipe r WHERE LOWER(r.dish.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Recipe> findByDishNameContainingIgnoreCase(@Param("name") String name);

    List<Recipe> findAllByOrderByCreatedAtDesc();
}
