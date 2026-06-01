package com.dishhunt.service;

import com.dishhunt.dto.UserProfileDto;
import com.dishhunt.entity.User;
import com.dishhunt.repository.RecipeRepository;
import com.dishhunt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    @Transactional(readOnly = true)
    public UserProfileDto getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        long count = recipeRepository.findByAuthorIdOrderByCreatedAtDesc(userId).size();
        return new UserProfileDto(user.getId(), user.getUsername(), user.getName(),
                user.getBio(), user.getNationality(), user.getJoinDate(), count);
    }

    @Transactional(readOnly = true)
    public UserProfileDto getProfileByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        long count = recipeRepository.findByAuthorIdOrderByCreatedAtDesc(user.getId()).size();
        return new UserProfileDto(user.getId(), user.getUsername(), user.getName(),
                user.getBio(), user.getNationality(), user.getJoinDate(), count);
    }
}
