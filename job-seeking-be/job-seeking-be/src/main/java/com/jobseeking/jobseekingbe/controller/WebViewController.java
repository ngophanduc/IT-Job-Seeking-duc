package com.jobseeking.jobseekingbe.controller;

import com.jobseeking.jobseekingbe.dto.ApiResponse;
import com.jobseeking.jobseekingbe.dto.response.*;
import com.jobseeking.jobseekingbe.entity.Post;
import com.jobseeking.jobseekingbe.service.imp.WebViewServiceImp;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/")
@RequiredArgsConstructor
public class WebViewController {

    WebViewServiceImp webViewServiceImp;

    @GetMapping()
    public ApiResponse<List<ProvinceDTO>> getAllProvinces() {
        return ApiResponse.<List<ProvinceDTO>>builder()
                .result(webViewServiceImp.getListProvince())
                .build();
    }

    @GetMapping("/company")
    public ApiResponse<List<CompanyDTO>> getAllEmployers() {
        return ApiResponse.<List<CompanyDTO>>builder()
                .result(webViewServiceImp.getAllEmployers())
                .build();
    }

    @GetMapping("/company/{id}")
    public ApiResponse<CompanyDTO> getCompanyDetail(@PathVariable String id) {
        return ApiResponse.<CompanyDTO>builder()
                .result(webViewServiceImp.getCompanyById(id))
                .build();
    }

    @GetMapping("/status")
    public ApiResponse<List<PostStatusDTO>> getPostStatus() {
        return ApiResponse.<List<PostStatusDTO>>builder()
                .result(webViewServiceImp.getPostStatus())
                .build();
    }
    @PostMapping("/job")
    public ApiResponse<List<PostDTO>> filterPost() {
        return ApiResponse.<List<PostDTO>>builder()
                .build();
    }
}
