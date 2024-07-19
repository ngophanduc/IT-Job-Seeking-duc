package com.jobseeking.jobseekingbe.controller;

import com.jobseeking.jobseekingbe.dto.request.BlockUserRequest;
import com.jobseeking.jobseekingbe.dto.response.BlockUserResponse;
import com.jobseeking.jobseekingbe.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/blockUser")
    public ResponseEntity<BlockUserResponse> blockUser(@RequestBody BlockUserRequest request) {
        return ResponseEntity.ok(adminService.blockUser(request));
    }
}
