package com.jobseeking.jobseekingbe.service;

import com.jobseeking.jobseekingbe.dto.request.EmployerUpdateRequest;
import com.jobseeking.jobseekingbe.dto.request.PostCreationRequest;
import com.jobseeking.jobseekingbe.dto.response.CandidateDTO;
import com.jobseeking.jobseekingbe.dto.response.EmployerDTO;
import com.jobseeking.jobseekingbe.dto.response.ProvinceDTO;
import com.jobseeking.jobseekingbe.entity.Candidate;
import com.jobseeking.jobseekingbe.entity.Employer;
import com.jobseeking.jobseekingbe.entity.Province;
import com.jobseeking.jobseekingbe.repository.EmployerRepository;
import com.jobseeking.jobseekingbe.repository.ProvinceRepository;
import com.jobseeking.jobseekingbe.repository.UserRepository;
import com.jobseeking.jobseekingbe.service.imp.EmployerServiceImp;
import com.jobseeking.jobseekingbe.service.imp.PostServiceImp;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmployerService implements EmployerServiceImp {

    EmployerRepository employerRepository;
    ProvinceRepository provinceRepository;

    @Override
    public Employer getEmployerById(String id) {
        Employer employer = employerRepository.findEmployerById(id);
        if(employer == null) {
            throw new RuntimeException("Employer is not found");
        }
        return employer;
    }

    @Override
    public String saveEmployer(Employer employer) {
        employerRepository.save(employer);
        return employer.getId();
    }

    @Override
    public EmployerDTO getEmployerInfo(String id) {
        Employer employer = getEmployerById(id);
        Province province = employer.getProvince();
        if(province != null) {
            ProvinceDTO provinceDTO = ProvinceDTO.builder()
                    .id(employer.getProvince().getProvinceId())
                    .value(employer.getProvince().getProvinceName())
                    .build();
        }

        return EmployerDTO.builder()
                .email(employer.getEmail() != null ? employer.getEmail() : "")
                .companyName(employer.getCompanyName() != null ? employer.getCompanyName() : "")
                .phone(employer.getPhone() != null ? employer.getPhone() : "")
                .website(employer.getWebsite() != null ? employer.getWebsite() : "")
                .companyDesc(employer.getAbout() != null ? employer.getAbout() : "")
                .facebook(employer.getFacebook() != null ? employer.getFacebook() : "")
                .linkedIn(employer.getLinkedin() != null ? employer.getLinkedin() : "")
                .locationId(employer.getProvince() != null ? employer.getProvince().getProvinceId() : 0)
                .build();
    }

    @Override
    public boolean updateEmployerInfo(String id, EmployerUpdateRequest employerUpdateRequest) {

        Employer employer = getEmployerById(id);

        if(!employerUpdateRequest.getCompanyName().isEmpty()) {
            employer.setCompanyName(employerUpdateRequest.getCompanyName());
        }
        if(!employerUpdateRequest.getPhone().isEmpty()) {
            employer.setPhone(employerUpdateRequest.getPhone());
        }
        if(!employerUpdateRequest.getWebsite().isEmpty()) {
            employer.setWebsite(employerUpdateRequest.getWebsite());
        }
        if(!employerUpdateRequest.getCompanyDesc().isEmpty()) {
            employer.setAbout(employerUpdateRequest.getCompanyDesc());
        }
        if(!employerUpdateRequest.getFacebook().isEmpty()) {
            employer.setFacebook(employerUpdateRequest.getFacebook());
        }
        if(!employerUpdateRequest.getLinkedIn().isEmpty()) {
            employer.setLinkedin(employerUpdateRequest.getLinkedIn());
        }
        if(employerUpdateRequest.getLocation() != 0) {
            Province province = provinceRepository.findById(employerUpdateRequest.getLocation())
                    .orElseThrow(() -> new RuntimeException("Province not found"));
            employer.setProvince(province);
        }
        employerRepository.save(employer);
        return true;
    }

}
