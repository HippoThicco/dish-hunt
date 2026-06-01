package com.dishhunt.service;

import com.dishhunt.dto.CommentRequest;
import com.dishhunt.dto.CommentResponse;
import com.dishhunt.dto.UserSummaryDto;
import com.dishhunt.entity.Comment;
import com.dishhunt.entity.Recipe;
import com.dishhunt.entity.User;
import com.dishhunt.repository.CommentRepository;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long recipeId) {
        return commentRepository.findByRecipeIdOrderByCreatedAtDesc(recipeId)
                .stream()
                .map(c -> new CommentResponse(
                        c.getId(),
                        new UserSummaryDto(c.getAuthor().getId(), c.getAuthor().getUsername(), c.getAuthor().getName()),
                        c.getContent(),
                        c.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponse addComment(Long recipeId, CommentRequest request, String username) {
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));

        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setRecipe(recipe);
        comment.setContent(request.getContent());

        Comment saved = commentRepository.save(comment);
        return new CommentResponse(
                saved.getId(),
                new UserSummaryDto(author.getId(), author.getUsername(), author.getName()),
                saved.getContent(),
                saved.getCreatedAt()
        );
    }
}
