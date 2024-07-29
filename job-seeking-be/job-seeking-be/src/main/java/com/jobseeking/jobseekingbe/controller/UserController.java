package com.jobseeking.jobseekingbe.controller;

import com.jobseeking.jobseekingbe.dto.ApiResponse;
import com.jobseeking.jobseekingbe.dto.request.*;
import com.jobseeking.jobseekingbe.dto.response.AuthenticationResponse;
import com.jobseeking.jobseekingbe.dto.response.CandidateDTO;
import com.jobseeking.jobseekingbe.dto.response.EmployerDTO;
import com.jobseeking.jobseekingbe.dto.response.UserDTO;
import com.jobseeking.jobseekingbe.entity.Employer;
import com.jobseeking.jobseekingbe.service.imp.AuthenticationServiceImp;
import com.jobseeking.jobseekingbe.service.imp.CandidateServiceImp;
import com.jobseeking.jobseekingbe.service.imp.EmployerServiceImp;
import com.jobseeking.jobseekingbe.service.imp.UserServiceImp;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController {

    UserServiceImp userServiceImp;
    AuthenticationServiceImp authenticationServiceImp;
    CandidateServiceImp candidateServiceImp;
    EmployerServiceImp employerServiceImp;

    @PostMapping()
    public ApiResponse<AuthenticationResponse> accountRegister(@RequestBody UserCreationRequest userCreationRequest) {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationServiceImp.accountRegister(userCreationRequest))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserDTO> getUserById(@PathVariable String id) {
        return ApiResponse.<UserDTO>builder()
                .result(userServiceImp.getUserById(id))
                .build();
    }

    @PutMapping("/update/candidate/{id}")
    public ApiResponse<Boolean> updateCandidate(@PathVariable String id,
                                                @RequestBody CandidateUpdateRequest candidateUpdateRequest) throws ParseException {
        return ApiResponse.<Boolean>builder()
                .result(candidateServiceImp.updateCandidateInfo(id, candidateUpdateRequest))
                .build();
    }

    @GetMapping("/candidate/{id}")
    public ApiResponse<CandidateDTO> getCandidateInfo(@PathVariable String id){
        return ApiResponse.<CandidateDTO>builder()
                .result(candidateServiceImp.getCandidateInfo(id))
                .build();
    }

    @PutMapping("/update/employer/{id}")
    public ApiResponse<Boolean> updateEmployer(@PathVariable String id,
                                                @RequestBody EmployerUpdateRequest employerUpdateRequest) {
        return ApiResponse.<Boolean>builder()
                .result(employerServiceImp.updateEmployerInfo(id, employerUpdateRequest))
                .build();
    }

    @GetMapping("/employer/{id}")
    public ApiResponse<EmployerDTO> getEmployerInfo(@PathVariable String id){
        return ApiResponse.<EmployerDTO>builder()
                .result(employerServiceImp.getEmployerInfo(id))
                .build();
    }


}
