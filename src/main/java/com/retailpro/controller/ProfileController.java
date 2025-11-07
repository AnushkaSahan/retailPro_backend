package com.retailpro.controller;

import com.retailpro.model.UserProfile;
import com.retailpro.service.UserProfileService;
import com.retailpro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserProfileService userProfileService;
    private final UserService userService;

    @Autowired
    public ProfileController(UserProfileService userProfileService,
                             UserService userService) {
        this.userProfileService = userProfileService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserProfile> getProfile(Authentication auth) {
        Long userId = userService.findByUsername(auth.getName()).getId();
        return ResponseEntity.ok(userProfileService.getUserProfile(userId));
    }

    @PutMapping
    public ResponseEntity<UserProfile> updateProfile(
            @RequestBody UserProfile profileData,
            Authentication auth) {
        Long userId = userService.findByUsername(auth.getName()).getId();
        return ResponseEntity.ok(userProfileService.updateProfile(userId, profileData));
    }
}