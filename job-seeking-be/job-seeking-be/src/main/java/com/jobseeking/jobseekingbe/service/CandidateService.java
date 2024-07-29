package com.jobseeking.jobseekingbe.service;

import com.jobseeking.jobseekingbe.dto.request.CandidateUpdateRequest;
import com.jobseeking.jobseekingbe.dto.request.FileUploadRequest;
import com.jobseeking.jobseekingbe.dto.response.CVDTO;
import com.jobseeking.jobseekingbe.dto.response.CandidateDTO;
import com.jobseeking.jobseekingbe.dto.response.CompanyDTO;
import com.jobseeking.jobseekingbe.dto.response.PostSaveDTO;
import com.jobseeking.jobseekingbe.entity.*;
import com.jobseeking.jobseekingbe.entity.keys.KeyCandidateCV;
import com.jobseeking.jobseekingbe.entity.keys.KeyEmployerCandidate;
import com.jobseeking.jobseekingbe.entity.keys.KeyPostCandidate;
import com.jobseeking.jobseekingbe.repository.*;
import com.jobseeking.jobseekingbe.service.imp.CandidateServiceImp;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CandidateService implements CandidateServiceImp {

    CandidateRepository candidateRepository;
    EmployerRepository employerRepository;
    ProvinceRepository provinceRepository;
    PostRepository postRepository;
    PostSaveRepository postSaveRepository;
    EmployerFollowRepository employerFollowRepository;
    CVRepository cvRepository;
    CandidateCVRepository candidateCVRepository;

    @Override
    public Candidate getCandidateById(String id) {
        Candidate candidate = candidateRepository.findCandidateById(id);
        if(candidate == null) {
            throw new RuntimeException("Candidate is not found");
        }
        return candidate;
    }

    @Override
    public CandidateDTO getCandidateInfo(String id) {
        Candidate candidate = getCandidateById(id);
        String dob = "";
        if(candidate.getDob() != null) {
            dob = formatDate(candidate.getDob());
        }
        return CandidateDTO.builder()
                .email(candidate.getEmail() != null ? candidate.getEmail() : "")
                .fullName(candidate.getFullName() != null ? candidate.getFullName() : "")
                .dob(dob)
                .gender(candidate.getGender() != null ? candidate.getGender() : "")
                .phone(candidate.getPhone() != null ? candidate.getPhone() : "")
                .workPosition(candidate.getPosition() != null ? candidate.getPosition() : "")
                .currentSalary(candidate.getCurrentSalary() != null ? candidate.getCurrentSalary() : "")
                .selfDesc(candidate.getSelfDesc() != null ? candidate.getSelfDesc() : "")
                .age(candidate.getAge() != null ? candidate.getAge() : "")
                .salaryExpect(candidate.getSalaryExpect() != null ? candidate.getSalaryExpect() : "")
                .facebook(candidate.getFacebook() != null ? candidate.getFacebook() : "")
                .linkedIn(candidate.getLinkedin() != null ? candidate.getLinkedin() : "")
                .location(candidate.getProvince() != null ? candidate.getProvince().getProvinceName() : "")
                .build();
    }

    @Override
    public boolean updateCandidateInfo(String id, CandidateUpdateRequest candidateUpdateRequest) throws ParseException {
        Candidate candidate = getCandidateById(id);
        if(!candidateUpdateRequest.getFullName().isEmpty()) {
            candidate.setFullName(candidateUpdateRequest.getFullName());
        }
        if(!candidateUpdateRequest.getDob().isEmpty()) {
            candidate.setDob(parseDate(candidateUpdateRequest.getDob()));
        }
        if(!candidateUpdateRequest.getGender().isEmpty()) {
            candidate.setGender(candidateUpdateRequest.getGender());
        }
        if(!candidateUpdateRequest.getPhone().isEmpty()) {
            candidate.setPhone(candidateUpdateRequest.getPhone());
        }
        if(!candidateUpdateRequest.getWorkPosition().isEmpty()) {
            candidate.setPosition(candidateUpdateRequest.getWorkPosition());
        }
        if(!candidateUpdateRequest.getCurrentSalary().isEmpty()) {
            candidate.setCurrentSalary(candidateUpdateRequest.getCurrentSalary());
        }
        if(!candidateUpdateRequest.getSelfDesc().isEmpty()) {
            candidate.setSelfDesc(candidateUpdateRequest.getSelfDesc());
        }
        if(!candidateUpdateRequest.getAge().isEmpty()) {
            candidate.setAge(candidateUpdateRequest.getAge());
        }
        if(!candidateUpdateRequest.getSalaryExpect().isEmpty()) {
            candidate.setSalaryExpect(candidateUpdateRequest.getSalaryExpect());
        }
        if(!candidateUpdateRequest.getFacebook().isEmpty()) {
            candidate.setFacebook(candidateUpdateRequest.getFacebook());
        }
        if(!candidateUpdateRequest.getLinkedIn().isEmpty()) {
            candidate.setLinkedin(candidateUpdateRequest.getLinkedIn());
        }
        if(candidateUpdateRequest.getLocation() != 0) {
            Province province = provinceRepository.findById(candidateUpdateRequest.getLocation())
                    .orElseThrow(() -> new RuntimeException("Province not found"));
            candidate.setProvince(province);
        }
        candidateRepository.save(candidate);
        return true;
    }

    @Override
    public boolean savePost(String userId, int postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post is not found"));
        Candidate candidate = candidateRepository.findCandidateById(userId);
        KeyPostCandidate keyPostCandidate = KeyPostCandidate.builder()
                .postId(postId)
                .userId(userId)
                .build();
        PostSave postSave = PostSave.builder()
                .keyPostCandidate(keyPostCandidate)
                .post(post)
                .candidate(candidate)
                .build();
        postSaveRepository.save(postSave);
        return true;
    }

    @Transactional
    @Override
    public boolean deletePostSave(String userId, int postId) {
        KeyPostCandidate keyPostCandidate = KeyPostCandidate.builder()
                .userId(userId)
                .postId(postId)
                .build();
        postSaveRepository.deleteByKeyPostCandidate(keyPostCandidate);
        return true;
    }

    @Override
    public List<PostSaveDTO> getAllPostSave(String userId) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        List<PostSave> postSaves = postSaveRepository.findAllByKeyPostCandidateUserId(userId);
        List<PostSaveDTO> postSaveDTOS = new ArrayList<>();

        for ( PostSave postSave : postSaves ) {
            Post p = postSave.getPost();
            PostSaveDTO postSaveDTO = PostSaveDTO.builder()
                    .postId(p.getPostId())
                    .title(p.getJobTitle())
                    .companyName(p.getEmployer().getCompanyName())
                    .applicationDate(simpleDateFormat.format(p.getEndDate()))
                    .image(java.util.Base64.getDecoder().decode(p.getEmployer().getAvatar().getData()))
                    .build();
            postSaveDTOS.add(postSaveDTO);
        }

        return postSaveDTOS;
    }

    @Override
    public boolean followCompany(String userId, String companyId) {

        Employer employer = employerRepository.findEmployerById(companyId);
        Candidate candidate = candidateRepository.findCandidateById(userId);

        KeyEmployerCandidate keyEmployerCandidate = KeyEmployerCandidate.builder()
                .candidateId(userId)
                .employerId(companyId)
                .build();
        EmployerFollow employerFollow = EmployerFollow.builder()
                .keyEmployerCandidate(keyEmployerCandidate)
                .employer(employer)
                .candidate(candidate)
                .build();
        employerFollowRepository.save(employerFollow);
        return true;
    }

    @Transactional
    @Override
    public boolean unFollowCompany(String userId, String companyId) {

        KeyEmployerCandidate keyEmployerCandidate = KeyEmployerCandidate.builder()
                .candidateId(userId)
                .employerId(companyId)
                .build();
        employerFollowRepository.deleteByKeyEmployerCandidate(keyEmployerCandidate);
        return true;
    }

    @Override
    public List<CompanyDTO> getFollowCompanies(String userId) {

        List<EmployerFollow> employerFollows = employerFollowRepository.findAll();
        List<CompanyDTO> companyDTOS = new ArrayList<>();
        for ( EmployerFollow employerFollow : employerFollows ) {

            Employer employer = employerFollow.getEmployer();
            CompanyDTO companyDTO = CompanyDTO.builder()
                    .companyId(employer.getId())
                    .companyName(employer.getCompanyName())
                    .locationName(employer.getProvince() != null ? employer.getProvince().getProvinceName() : "")
                    .postCount(postRepository.getAllByEmployerId(employer.getId()).size())
                    .image(java.util.Base64.getDecoder().decode(employer.getAvatar().getData()))
                    .build();
            companyDTOS.add(companyDTO);
        }
        return companyDTOS;
    }

    @Override
    public boolean uploadCV(FileUploadRequest fileUploadRequest) throws IOException {

        Candidate candidate = candidateRepository.findCandidateById(fileUploadRequest.getUserId());
        CV cv = CV.builder()
                .cvName(fileUploadRequest.getFile().getOriginalFilename())
                .data(Base64.getEncoder().encodeToString(fileUploadRequest.getFile().getBytes()))
                .build();
        int cvId = saveCV(cv);
        KeyCandidateCV keyCandidateCV = KeyCandidateCV.builder()
                .cv_id(cvId)
                .candidateId(candidate.getId())
                .build();

        CandidateCV candidateCV = CandidateCV.builder()
                .keyCandidateCV(keyCandidateCV)
                .cv(cv)
                .candidate(candidate)
                .build();
        candidateCVRepository.save(candidateCV);
        return true;
    }

    @Override
    public List<CVDTO> getCVs(String userId) {
        List<CandidateCV> candidateCVS = candidateCVRepository.findAllByKeyCandidateCVCandidateId(userId);
        List<CVDTO> cvdtos = new ArrayList<>();
        for ( CandidateCV candidateCV : candidateCVS ) {
            CVDTO cvdto = CVDTO.builder()
                    .fileName(candidateCV.getCv().getCvName())
                    .data(java.util.Base64.getDecoder().decode(candidateCV.getCv().getData()))
                    .build();
            cvdtos.add(cvdto);
        }
        return cvdtos;
    }

    public int saveCV(CV cv) {
        cvRepository.save(cv);
        return cv.getCvId();
    }

    public Date parseDate(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
        return simpleDateFormat.parse(date);
    }

    public String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
        return simpleDateFormat.format(date);
    }
}
