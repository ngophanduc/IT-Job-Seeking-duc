package com.jobseeking.jobseekingbe.controller;

import com.jobseeking.jobseekingbe.dto.ApiResponse;
import com.jobseeking.jobseekingbe.dto.request.AuthenticationRequest;
import com.jobseeking.jobseekingbe.dto.request.ChangePasswordRequest;
import com.jobseeking.jobseekingbe.dto.request.IntrospectRequest;
import com.jobseeking.jobseekingbe.dto.request.ResetPasswordRequest;
import com.jobseeking.jobseekingbe.dto.response.AuthenticationResponse;
import com.jobseeking.jobseekingbe.dto.response.IntrospectResponse;
import com.jobseeking.jobseekingbe.service.imp.AuthenticationServiceImp;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@CrossOrigin
@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationController {

    AuthenticationServiceImp authenticationServiceImp;
    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationServiceImp.login(authenticationRequest))
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        return ApiResponse.<IntrospectResponse>builder()
                .result(authenticationServiceImp.introspect(introspectRequest))
                .build();
    }

    @PostMapping("/reset-password")
    ApiResponse<String> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        authenticationServiceImp.resetPassword(resetPasswordRequest.getEmail());
        return ApiResponse.<String>builder()
                .result("ch")
                .build();
    }

    @PostMapping("/change-password")
    public ApiResponse<Boolean> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) throws ParseException, JOSEException {
        return ApiResponse.<Boolean>builder()
                .result(authenticationServiceImp.changePassword(changePasswordRequest))
                .build();
    }







}
