package com.dishhunt.repository;

import com.dishhunt.entity.Favourite;
import com.dishhunt.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    Optional<Favourite> findByUserIdAndRecipeId(Long userId, Long recipeId);
    boolean existsByUserIdAndRecipeId(Long userId, Long recipeId);

    @Query("SELECT f.recipe FROM Favourite f WHERE f.user.id = :userId ORDER BY f.id DESC")
    List<Recipe> findRecipesByUserId(@Param("userId") Long userId);
}
