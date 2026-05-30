package com.dishhunt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private UserSummaryDto author;
    private String content;
    private LocalDateTime createdAt;
}
