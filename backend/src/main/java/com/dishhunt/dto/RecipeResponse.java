package com.dishhunt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeResponse {
    private Long id;
    private String title;
    private String description;
    private Integer calories;
    private String instructions;
    private LocalDateTime createdAt;
    private UserSummaryDto author;
    private String dishName;
    private String dishCuisine;
    private List<IngredientDto> ingredients;
    private long likeCount;
    private long commentCount;
    private boolean likedByCurrentUser;
    private boolean favouritedByCurrentUser;
}
