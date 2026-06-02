package com.dishhunt.controller;

import com.dishhunt.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/recipes/{recipeId}/like")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> toggleLike(
            @PathVariable Long recipeId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(likeService.toggleLike(recipeId, userDetails.getUsername()));
    }
}
