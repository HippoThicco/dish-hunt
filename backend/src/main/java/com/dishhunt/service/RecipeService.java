package com.dishhunt.service;

import com.dishhunt.dto.IngredientDto;
import com.dishhunt.dto.RecipeRequest;
import com.dishhunt.dto.RecipeResponse;
import com.dishhunt.dto.UserSummaryDto;
import com.dishhunt.entity.Dish;
import com.dishhunt.entity.Ingredient;
import com.dishhunt.entity.Recipe;
import com.dishhunt.entity.User;
import com.dishhunt.repository.DishRepository;
import com.dishhunt.repository.FavouriteRepository;
import com.dishhunt.repository.LikeRepository;
import com.dishhunt.repository.RecipeRepository;
import com.dishhunt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;
    private final LikeRepository likeRepository;
    private final FavouriteRepository favouriteRepository;

    @Transactional
    public RecipeResponse createRecipe(RecipeRequest request, String username) {
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Recipe recipe = new Recipe();
        recipe.setAuthor(author);
        recipe.setTitle(request.getTitle());
        recipe.setDescription(request.getDescription());
        recipe.setCalories(request.getCalories());
        recipe.setInstructions(request.getInstructions());

        if (request.getDishName() != null && !request.getDishName().isBlank()) {
            Dish dish = dishRepository.findByNameIgnoreCase(request.getDishName()).orElseGet(() -> {
                Dish d = new Dish();
                d.setName(request.getDishName());
                d.setCuisine(request.getDishCuisine());
                return dishRepository.save(d);
            });
            recipe.setDish(dish);
        }

        if (request.getIngredients() != null) {
            List<Ingredient> ingredients = request.getIngredients().stream().map(dto -> {
                Ingredient ing = new Ingredient();
                ing.setRecipe(recipe);
                ing.setName(dto.getName());
                ing.setQuantity(dto.getQuantity());
                ing.setUnit(dto.getUnit());
                return ing;
            }).collect(Collectors.toList());
            recipe.getIngredients().addAll(ingredients);
        }

        Recipe saved = recipeRepository.save(recipe);
        return toResponse(saved, null);
    }

    @Transactional(readOnly = true)
    public List<RecipeResponse> getAllRecipes(Long currentUserId) {
        return recipeRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(r -> toResponse(r, currentUserId))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RecipeResponse getRecipeById(Long id, Long currentUserId) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));
        return toResponse(recipe, currentUserId);
    }

    @Transactional(readOnly = true)
    public List<RecipeResponse> getRecipesByUser(Long userId, Long currentUserId) {
        return recipeRepository.findByAuthorIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(r -> toResponse(r, currentUserId))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RecipeResponse> searchByDish(String dishName, Long currentUserId) {
        return recipeRepository.findByDishNameContainingIgnoreCase(dishName)
                .stream()
                .map(r -> toResponse(r, currentUserId))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RecipeResponse> getFavouritesByUser(Long userId) {
        return favouriteRepository.findRecipesByUserId(userId)
                .stream()
                .map(r -> toResponse(r, userId))
                .collect(Collectors.toList());
    }

    public RecipeResponse toResponse(Recipe recipe, Long currentUserId) {
        long likeCount = likeRepository.countByRecipeId(recipe.getId());
        boolean liked = currentUserId != null && likeRepository.existsByUserIdAndRecipeId(currentUserId, recipe.getId());
        boolean favourited = currentUserId != null && favouriteRepository.existsByUserIdAndRecipeId(currentUserId, recipe.getId());

        List<IngredientDto> ingredients = recipe.getIngredients().stream()
                .map(i -> new IngredientDto(i.getName(), i.getQuantity(), i.getUnit()))
                .collect(Collectors.toList());

        User author = recipe.getAuthor();
        Dish dish = recipe.getDish();

        return RecipeResponse.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .description(recipe.getDescription())
                .calories(recipe.getCalories())
                .instructions(recipe.getInstructions())
                .createdAt(recipe.getCreatedAt())
                .author(new UserSummaryDto(author.getId(), author.getUsername(), author.getName()))
                .dishName(dish != null ? dish.getName() : null)
                .dishCuisine(dish != null ? dish.getCuisine() : null)
                .ingredients(ingredients)
                .likeCount(likeCount)
                .likedByCurrentUser(liked)
                .favouritedByCurrentUser(favourited)
                .build();
    }
}
