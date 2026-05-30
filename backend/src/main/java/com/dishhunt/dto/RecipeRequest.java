package com.dishhunt.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class RecipeRequest {
    @NotBlank
    private String title;

    private String description;
    private Integer calories;

    @NotBlank
    private String instructions;

    private String dishName;
    private String dishCuisine;

    private List<IngredientDto> ingredients;
}
