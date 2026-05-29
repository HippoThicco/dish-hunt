package com.dishhunt.repository;

import com.dishhunt.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndRecipeId(Long userId, Long recipeId);
    long countByRecipeId(Long recipeId);
    boolean existsByUserIdAndRecipeId(Long userId, Long recipeId);
}
