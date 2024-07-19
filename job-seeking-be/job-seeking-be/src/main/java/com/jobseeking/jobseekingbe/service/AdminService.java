package com.jobseeking.jobseekingbe.service;

import com.jobseeking.jobseekingbe.dto.request.BlockUserRequest;
import com.jobseeking.jobseekingbe.dto.response.BlockUserResponse;
import com.jobseeking.jobseekingbe.entity.User;
import com.jobseeking.jobseekingbe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    public BlockUserResponse blockUser(BlockUserRequest request) {
        Optional<User> userOptional = userRepository.findById(request.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setBlocked(true);
            userRepository.save(user);
            return new BlockUserResponse("User blocked successfully", true);
        } else {
            return new BlockUserResponse("User not found", false);
        }
    }
}
