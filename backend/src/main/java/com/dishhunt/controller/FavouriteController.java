package com.dishhunt.controller;

import com.dishhunt.service.FavouriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/recipes/{recipeId}/favourite")
@RequiredArgsConstructor
public class FavouriteController {

    private final FavouriteService favouriteService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> toggleFavourite(
            @PathVariable Long recipeId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(favouriteService.toggleFavourite(recipeId, userDetails.getUsername()));
    }
}
