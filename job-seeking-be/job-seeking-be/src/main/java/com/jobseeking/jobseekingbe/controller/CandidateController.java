package com.jobseeking.jobseekingbe.controller;

import com.jobseeking.jobseekingbe.dto.ApiResponse;
import com.jobseeking.jobseekingbe.dto.request.FileUploadRequest;
import com.jobseeking.jobseekingbe.dto.response.CVDTO;
import com.jobseeking.jobseekingbe.dto.response.CompanyDTO;
import com.jobseeking.jobseekingbe.dto.response.PostSaveDTO;
import com.jobseeking.jobseekingbe.service.imp.CandidateServiceImp;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/candidate")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CandidateController {

    CandidateServiceImp candidateServiceImp;

    @GetMapping("/post-save/{id}")
    public ApiResponse<List<PostSaveDTO>> getPostSave(@PathVariable String id) {
        return ApiResponse.<List<PostSaveDTO>>builder()
                .result(candidateServiceImp.getAllPostSave(id))
                .build();
    }

    @PostMapping("/post-save/{userId}&{postId}")
    public ApiResponse<Boolean> savePost(@PathVariable int postId,
                                         @PathVariable String userId ) {
        return ApiResponse.<Boolean>builder()
                .result(candidateServiceImp.savePost(userId, postId))
                .build();
    }

    @DeleteMapping("/post-save/{userId}&{postId}")
    public ApiResponse<Boolean> deletePostSave(@PathVariable int postId,
                                         @PathVariable String userId ) {
        return ApiResponse.<Boolean>builder()
                .result(candidateServiceImp.deletePostSave(userId, postId))
                .build();
    }

    @PostMapping("/follow/{userId}&{companyId}")
    public ApiResponse<Boolean> followCompany(@PathVariable String userId,
                                         @PathVariable String companyId ) {
        return ApiResponse.<Boolean>builder()
                .result(candidateServiceImp.followCompany(userId, companyId))
                .build();
    }

    @DeleteMapping("/follow/{userId}&{companyId}")
    public ApiResponse<Boolean> unFollowCompany(@PathVariable String companyId,
                                               @PathVariable String userId ) {
        return ApiResponse.<Boolean>builder()
                .result(candidateServiceImp.unFollowCompany(userId, companyId))
                .build();
    }

    @GetMapping("/follow/{id}")
    public ApiResponse<List<CompanyDTO>> getFollowCompany(@PathVariable String id) {
        return ApiResponse.<List<CompanyDTO>>builder()
                .result(candidateServiceImp.getFollowCompanies(id))
                .build();
    }

    @PostMapping("/cv-upload")
    public ApiResponse<Boolean> uploadCV(@ModelAttribute FileUploadRequest fileUploadRequest) throws IOException {
        return ApiResponse.<Boolean>builder()
                .result(candidateServiceImp.uploadCV(fileUploadRequest))
                .build();
    }

    @GetMapping("/cv/{id}")
    public ApiResponse<List<CVDTO>> getCV(@PathVariable String id){
        return ApiResponse.<List<CVDTO>>builder()
                .result(candidateServiceImp.getCVs(id))
                .build();
    }

}
