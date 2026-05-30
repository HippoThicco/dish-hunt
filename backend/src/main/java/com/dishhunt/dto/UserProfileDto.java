package com.dishhunt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserProfileDto {
    private Long id;
    private String username;
    private String name;
    private String bio;
    private String nationality;
    private LocalDate joinDate;
    private long recipeCount;
}
