package com.jobseeking.jobseekingbe.service;

import com.jobseeking.jobseekingbe.dto.request.AuthenticationRequest;
import com.jobseeking.jobseekingbe.dto.request.ChangePasswordRequest;
import com.jobseeking.jobseekingbe.dto.request.IntrospectRequest;
import com.jobseeking.jobseekingbe.dto.request.UserCreationRequest;
import com.jobseeking.jobseekingbe.dto.response.AuthenticationResponse;
import com.jobseeking.jobseekingbe.dto.response.IntrospectResponse;
import com.jobseeking.jobseekingbe.entity.*;
import com.jobseeking.jobseekingbe.repository.*;
import com.jobseeking.jobseekingbe.service.imp.AuthenticationServiceImp;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j

public class AuthenticationService implements AuthenticationServiceImp {

    UserRepository userRepository;
    EmployerRepository employerRepository;
    CandidateRepository candidateRepository;
    AdminRepository adminRepository;
    EmailService emailService;
    RoleRepository roleRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @Override
    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException {

        var token = introspectRequest.getToken();

        JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(jwsVerifier);

        return IntrospectResponse.builder()
                .email(getEmailFromToken(token))
                .valid(verified && expirationTime.after(new Date()))
                .build();
    }

    public AuthenticationResponse accountRegister(UserCreationRequest userCreationRequest) {

        if(userRepository.existsByEmail(userCreationRequest.getEmail())) {
            throw new RuntimeException("Email existed");
        }

        Role role = roleRepository.findById(userCreationRequest.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role Id not found"));

        if(role.getRoleName().equals("EMPLOYER")) {
            Employer employer = Employer.builder()
                    .email(userCreationRequest.getEmail())
                    .password(userCreationRequest.getPassword())
                    .phone(userCreationRequest.getPhone())
                    .companyName(userCreationRequest.getCompanyName())
                    .role(role)
                    .build();
            employerRepository.save(employer);
        }
        if(role.getRoleName().equals("CANDIDATE")) {
            Candidate candidate = Candidate.builder()
                    .email(userCreationRequest.getEmail())
                    .password(userCreationRequest.getPassword())
                    .phone(userCreationRequest.getPhone())
                    .role(role)
                    .build();
            candidateRepository.save(candidate);
        }

        if(role.getRoleName().equals("ADMIN")) {
            Admin admin = Admin.builder()
                    .email(userCreationRequest.getEmail())
                    .password(userCreationRequest.getPassword())
                    .phone(userCreationRequest.getPhone())
                    .role(role)
                    .build();
            adminRepository.save(admin);
        }

        User user = userRepository.findByEmail(userCreationRequest.getEmail());
        if(user == null) {
            throw new RuntimeException("Email is not found");
        }

        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .userId(user.getId())
                .token(generateToken(user.getEmail()))
                .userRole(user.getRole().getRoleName())
                .build();

        return authenticationResponse;
    }
    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {

        boolean authenticated = false;
        String token = null;
        String userRole = null;
        String userId = null;

        if(userRepository.existsByEmail(authenticationRequest.getEmail())) {
            User user = userRepository.findByEmail(authenticationRequest.getEmail());
            authenticated = user.getPassword().equals(authenticationRequest.getPassword());
            if(authenticated) {
                userRole = user.getRole().getRoleName();
                userId = user.getId();
                token = generateToken(authenticationRequest.getEmail());
            }
        };

        return AuthenticationResponse.builder()
                .userRole(userRole)
                .userId(userId)
                .token(token)
                .build();
    }

    @Override
    public String generateToken(String email) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(email)
                .issuer("jobseeking.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
                ))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void resetPassword(String email) {

        User user = userRepository.getUserByEmail(email);
        if(user == null) {
            throw new RuntimeException("Email not found");
        }
        String randomPassword = UUID.randomUUID().toString();
        String message = "Here is your reset password:\n" + randomPassword;
        updatePassword(email, randomPassword);
        emailService.sendEmail(email, "Password Recovery", message);
    }
    @Override
    public boolean updatePassword (String email, String newPassword) {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new RuntimeException("Email is not found");
        }
        user.setPassword(newPassword);
        userRepository.save(user);
        return true;
    }
    @Override
    public boolean changePassword(ChangePasswordRequest changePasswordRequest) throws ParseException, JOSEException {
        String email = getEmailFromToken(changePasswordRequest.getToken());
        return updatePassword(email, changePasswordRequest.getNewPassword());
    }

    @Override
    public String getEmailFromToken(String token) throws JOSEException, ParseException {

        JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(jwsVerifier);

        return signedJWT.getJWTClaimsSet().getSubject();
    }
}
