package com.jobseeking.jobseekingbe.service;

import com.jobseeking.jobseekingbe.dto.request.FileUploadRequest;
import com.jobseeking.jobseekingbe.entity.Avatar;
import com.jobseeking.jobseekingbe.entity.Employer;
import com.jobseeking.jobseekingbe.entity.User;
import com.jobseeking.jobseekingbe.repository.AvatarRepository;
import com.jobseeking.jobseekingbe.repository.EmployerRepository;
import com.jobseeking.jobseekingbe.repository.UserRepository;
import com.jobseeking.jobseekingbe.service.imp.FileStorageServiceImp;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileStorageService implements FileStorageServiceImp {

    UserRepository userRepository;
    AvatarRepository avatarRepository;
    EmployerRepository employerRepository;

    @Override
    public void save(FileUploadRequest fileUploadRequest) {
        String userId = fileUploadRequest.getUserId();
        Optional<Avatar> avatar = avatarRepository.findById(userId);
        User user = userRepository.getUserById(userId);
        MultipartFile file = fileUploadRequest.getFile();
        try {
            if( avatar.isPresent() ) {
                avatar.get().setData(Base64.getEncoder().encodeToString(file.getBytes()));
                avatar.get().setName(file.getOriginalFilename());
                avatar.get().setType(file.getContentType());
                avatarRepository.save(avatar.get());
            } else {
                Avatar newAvatar = Avatar.builder()
                        .user(user)
                        .name(StringUtils.cleanPath(file.getOriginalFilename()))
                        .type(file.getContentType())
                        .data(Base64.getEncoder().encodeToString(file.getBytes()))
                        .build();
                avatarRepository.save(newAvatar);
            }
        } catch (Exception e) {
            throw new RuntimeException("Upload image error");
        }
    }

    @Override
    public void updateBackground(FileUploadRequest fileUploadRequest) {
        String userId = fileUploadRequest.getUserId();
        Employer employer = employerRepository.findEmployerById(userId);
        MultipartFile file = fileUploadRequest.getFile();
        try {
            employer.setBackground(Base64.getEncoder().encodeToString(file.getBytes()));
            employerRepository.save(employer);
        } catch (Exception e) {
            throw new RuntimeException("Upload background error");
        }
    }

    @Override
    public String getBackground(String userId) {
        Employer employer = employerRepository.findEmployerById(userId);
        return employer.getBackground();
    }

    @Override
    public Avatar getAvatar(String userId) {
        Optional<Avatar> avatar = avatarRepository.findById(userId);
        if( avatar.isPresent() ) {
            return avatar.get();
        } else return null;
    }
}
