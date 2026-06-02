package com.dishhunt.controller;

import com.dishhunt.dto.RecipeResponse;
import com.dishhunt.dto.UserProfileDto;
import com.dishhunt.entity.User;
import com.dishhunt.repository.UserRepository;
import com.dishhunt.service.RecipeService;
import com.dishhunt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RecipeService recipeService;
    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getProfileByUsername(userDetails.getUsername()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getProfile(id));
    }

    @GetMapping("/{id}/recipes")
    public ResponseEntity<List<RecipeResponse>> getUserRecipes(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long currentUserId = resolveUserId(userDetails);
        return ResponseEntity.ok(recipeService.getRecipesByUser(id, currentUserId));
    }

    @GetMapping("/me/favourites")
    public ResponseEntity<List<RecipeResponse>> getMyFavourites(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = resolveUserId(userDetails);
        return ResponseEntity.ok(recipeService.getFavouritesByUser(userId));
    }

    private Long resolveUserId(UserDetails userDetails) {
        if (userDetails == null) return null;
        return userRepository.findByUsername(userDetails.getUsername())
                .map(User::getId)
                .orElse(null);
    }
}
