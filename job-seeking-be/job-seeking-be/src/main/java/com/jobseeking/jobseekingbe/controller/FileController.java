package com.jobseeking.jobseekingbe.controller;

import com.jobseeking.jobseekingbe.dto.ApiResponse;
import com.jobseeking.jobseekingbe.dto.request.FileUploadRequest;
import com.jobseeking.jobseekingbe.entity.Avatar;
import com.jobseeking.jobseekingbe.service.imp.FileStorageServiceImp;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    FileStorageServiceImp fileStorageServiceImp;

    @PostMapping("/avatar")
    public ApiResponse<String> uploadAvatar(@ModelAttribute FileUploadRequest fileUploadRequest) {
        fileStorageServiceImp.save(fileUploadRequest);
        String message = "Upload file successful!";
        return ApiResponse.<String>builder()
                .result(message)
                .build();
    }

    @PostMapping("/background")
    public ApiResponse<String> uploadBackground(@ModelAttribute FileUploadRequest fileUploadRequest) {
        fileStorageServiceImp.updateBackground(fileUploadRequest);
        String message = "Upload file successful!";
        return ApiResponse.<String>builder()
                .result(message)
                .build();
    }

    @GetMapping("/avatar/{id}")
    public ApiResponse<byte[]> readImage(@PathVariable String id) {
        Avatar avatar = fileStorageServiceImp.getAvatar(id);
        if(avatar != null) {
            byte[] imageBytes = java.util.Base64.getDecoder().decode(avatar.getData());
            return ApiResponse.<byte[]>builder()
                    .result(imageBytes)
                    .build();
        }
        return null;
    }

    @GetMapping("/background/{id}")
    public ApiResponse<byte[]> readBackground(@PathVariable String id) {
        String data = fileStorageServiceImp.getBackground(id);
        byte[] imageBytes = java.util.Base64.getDecoder().decode(data);
        return ApiResponse.<byte[]>builder()
                .result(imageBytes)
                .build();
    }
}
