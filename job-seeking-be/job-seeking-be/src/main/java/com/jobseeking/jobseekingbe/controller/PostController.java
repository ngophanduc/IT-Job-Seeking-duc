package com.jobseeking.jobseekingbe.controller;

import com.jobseeking.jobseekingbe.dto.ApiResponse;
import com.jobseeking.jobseekingbe.dto.request.PostCreationRequest;
import com.jobseeking.jobseekingbe.dto.request.PostFilterRequest;
import com.jobseeking.jobseekingbe.dto.request.PostUpdateRequest;
import com.jobseeking.jobseekingbe.dto.response.*;
import com.jobseeking.jobseekingbe.service.imp.PostServiceImp;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/post")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PostController {

    PostServiceImp postServiceImp;

    @GetMapping("/active")
    public ApiResponse<List<PostDTO>> getAllActivePost() {
        return ApiResponse.<List<PostDTO>>builder()
                .result(postServiceImp.getAllActivePost())
                .build();
    }

    @GetMapping("/admin")
    public ApiResponse<List<PostDTO>> getAllPost() {
        return ApiResponse.<List<PostDTO>>builder()
                .result(postServiceImp.getAllPost())
                .build();
    }

    @GetMapping("/status/{id}")
    public ApiResponse<List<PostDTO>> getPostByStatus(@PathVariable int id) {
        return ApiResponse.<List<PostDTO>>builder()
                .result(postServiceImp.getPostByStatus(id))
                .build();
    }

    @GetMapping("/user/{id}")
    public ApiResponse<List<PostDTO>> getAllByEmployerId(@PathVariable String id) {
        return ApiResponse.<List<PostDTO>>builder()
                .result(postServiceImp.getAllPostByEmployerId(id))
                .build();
    }

    @PostMapping("/status/{id}")
    public ApiResponse<Boolean> activePost(@PathVariable int id) {
        return ApiResponse.<Boolean>builder()
                .result(postServiceImp.activePost(id))
                .build();
    }

    @PostMapping("/reject/{id}")
    public ApiResponse<Boolean> rejectPost(@PathVariable int id) {
        return ApiResponse.<Boolean>builder()
                .result(postServiceImp.rejectPost(id))
                .build();
    }

    @GetMapping("/pending")
    public ApiResponse<List<PostDTO>> getPendingPost() {
        return ApiResponse.<List<PostDTO>>builder()
                .result(postServiceImp.getAllPendingPosts())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<PostDTO> getPostById(@PathVariable int id) {
        return ApiResponse.<PostDTO>builder()
                .result(postServiceImp.getPostById(id))
                .build();
    }

    @GetMapping("/type")
    public ApiResponse<List<PostTypeDTO>> getPostTypes() {
        return ApiResponse.<List<PostTypeDTO>>builder()
                .result(postServiceImp.getPostTypes())
                .build();
    }

    @GetMapping("/level")
    public ApiResponse<List<PostLevelDTO>> getPostLevels() {
        return ApiResponse.<List<PostLevelDTO>>builder()
                .result(postServiceImp.getPostLevels())
                .build();
    }

    @GetMapping("/contract")
    public ApiResponse<List<PostContractDTO>> getPostContracts() {
        return ApiResponse.<List<PostContractDTO>>builder()
                .result(postServiceImp.getPostContracts())
                .build();
    }

    @PostMapping()
    public ApiResponse<AuthenticationResponse> addPost(@RequestBody PostCreationRequest postCreationRequest) {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(postServiceImp.addPost(postCreationRequest))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Boolean> updatePost(
            @PathVariable int id,
            @RequestBody PostUpdateRequest postUpdateRequest) {
        return ApiResponse.<Boolean>builder()
                .result(postServiceImp.updatePost(id, postUpdateRequest))
                .build();
    }

    @PostMapping("/{userId}")
    public ApiResponse<Boolean> addPostWhenAuthenticated(@PathVariable String userId,
                                                                        @RequestBody PostCreationRequest postCreationRequest) {
        return ApiResponse.<Boolean>builder()
                .result(postServiceImp.addPostWhenAuthenticated(userId, postCreationRequest))
                .build();
    }

    @PostMapping("/filter")
    public ApiResponse<List<PostDTO>> filterPost(@RequestBody PostFilterRequest postFilterRequest) {
        return ApiResponse.<List<PostDTO>>builder()
                .result(postServiceImp.filterPost(postFilterRequest))
                .build();
    }

}
