package com.dishhunt.service;

import com.dishhunt.entity.Favourite;
import com.dishhunt.entity.Recipe;
import com.dishhunt.entity.User;
import com.dishhunt.repository.FavouriteRepository;
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
public class FavouriteService {

    private final FavouriteRepository favouriteRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Map<String, Object> toggleFavourite(Long recipeId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));

        Optional<Favourite> existing = favouriteRepository.findByUserIdAndRecipeId(user.getId(), recipeId);
        boolean favourited;
        if (existing.isPresent()) {
            favouriteRepository.delete(existing.get());
            favourited = false;
        } else {
            Favourite fav = new Favourite();
            fav.setUser(user);
            fav.setRecipe(recipe);
            favouriteRepository.save(fav);
            favourited = true;
        }

        return Map.of("favourited", favourited);
    }
}
