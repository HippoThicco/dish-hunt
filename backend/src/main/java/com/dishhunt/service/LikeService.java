package com.dishhunt.service;

import com.dishhunt.entity.Like;
import com.dishhunt.entity.Recipe;
import com.dishhunt.entity.User;
import com.dishhunt.repository.LikeRepository;
import com.dishhunt.repository.RecipeRepository;
import com.dishhunt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Map<String, Object> toggleLike(Long recipeId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));

        Optional<Like> existing = likeRepository.findByUserIdAndRecipeId(user.getId(), recipeId);
        boolean liked;
        if (existing.isPresent()) {
            likeRepository.delete(existing.get());
            liked = false;
        } else {
            Like like = new Like();
            like.setUser(user);
            like.setRecipe(recipe);
            likeRepository.save(like);
            liked = true;
        }

        long count = likeRepository.countByRecipeId(recipeId);
        return Map.of("liked", liked, "likeCount", count);
    }
}
