package com.retailpro.service;

import com.retailpro.model.UserProfile;
import com.retailpro.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfile getUserProfile(Long userId) {
        return userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    public UserProfile updateProfile(Long userId, UserProfile profileData) {
        UserProfile profile = getUserProfile(userId);
        profile.setPhone(profileData.getPhone());
        profile.setEmail(profileData.getEmail());
        profile.setAddress(profileData.getAddress());
        profile.setAvatar(profileData.getAvatar());
        return userProfileRepository.save(profile);
    }
}