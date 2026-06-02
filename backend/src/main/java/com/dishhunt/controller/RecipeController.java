package com.dishhunt.controller;

import com.dishhunt.dto.RecipeRequest;
import com.dishhunt.dto.RecipeResponse;
import com.dishhunt.entity.User;
import com.dishhunt.repository.UserRepository;
import com.dishhunt.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<RecipeResponse>> getAll(
            @RequestParam(required = false) String dish,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long currentUserId = resolveUserId(userDetails);
        if (dish != null && !dish.isBlank()) {
            return ResponseEntity.ok(recipeService.searchByDish(dish, currentUserId));
        }
        return ResponseEntity.ok(recipeService.getAllRecipes(currentUserId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(recipeService.getRecipeById(id, resolveUserId(userDetails)));
    }

    @PostMapping
    public ResponseEntity<RecipeResponse> create(
            @Valid @RequestBody RecipeRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(recipeService.createRecipe(request, userDetails.getUsername()));
    }

    private Long resolveUserId(UserDetails userDetails) {
        if (userDetails == null) return null;
        return userRepository.findByUsername(userDetails.getUsername())
                .map(User::getId)
                .orElse(null);
    }
}
