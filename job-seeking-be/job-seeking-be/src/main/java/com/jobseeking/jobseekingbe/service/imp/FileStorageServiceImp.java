package com.jobseeking.jobseekingbe.service.imp;

import com.jobseeking.jobseekingbe.dto.request.FileUploadRequest;
import com.jobseeking.jobseekingbe.entity.Avatar;

public interface FileStorageServiceImp {

    void save(FileUploadRequest fileUploadRequest);

    void updateBackground(FileUploadRequest fileUploadRequest);

    String getBackground(String userId);

    Avatar getAvatar(String userId);
}
