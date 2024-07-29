package com.jobseeking.jobseekingbe.service;

import com.jobseeking.jobseekingbe.dto.response.*;
import com.jobseeking.jobseekingbe.entity.Employer;
import com.jobseeking.jobseekingbe.entity.Post;
import com.jobseeking.jobseekingbe.entity.PostStatus;
import com.jobseeking.jobseekingbe.entity.Province;
import com.jobseeking.jobseekingbe.repository.EmployerRepository;
import com.jobseeking.jobseekingbe.repository.PostRepository;
import com.jobseeking.jobseekingbe.repository.PostStatusRepository;
import com.jobseeking.jobseekingbe.repository.ProvinceRepository;
import com.jobseeking.jobseekingbe.service.imp.WebViewServiceImp;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WebViewService implements WebViewServiceImp {

    ProvinceRepository provinceRepository;
    PostStatusRepository postStatusRepository;
    EmployerRepository employerRepository;
    PostRepository postRepository;
    @Override
    public List<ProvinceDTO> getListProvince() {
        List<Province> provinces = provinceRepository.findAll();
        List<ProvinceDTO> provinceDTOS = new ArrayList<>();
        for(Province p : provinces) {
            ProvinceDTO provinceDTO = ProvinceDTO.builder()
                    .id(p.getProvinceId())
                    .value(p.getProvinceName())
                    .build();
            provinceDTOS.add(provinceDTO);
        }

        return provinceDTOS;
    }

    @Override
    public List<PostStatusDTO> getPostStatus() {

        List<PostStatus> postStatuses = postStatusRepository.findAll();
        List<PostStatusDTO> postStatusDTOS = new ArrayList<>();
        for ( PostStatus p : postStatuses ) {
            PostStatusDTO postStatusDTO = PostStatusDTO.builder()
                    .id(p.getStatusId())
                    .value(p.getStatusTitle())
                    .build();
            postStatusDTOS.add(postStatusDTO);
        }
        return postStatusDTOS;
    }

    @Override
    public List<CompanyDTO> getAllEmployers() {

        List<Employer> employers = employerRepository.findAll();
        List<CompanyDTO> companyDTOS = new ArrayList<>();

        for ( Employer e : employers ) {
            CompanyDTO companyDTO = CompanyDTO.builder()
                    .companyId(e.getId())
                    .companyName(e.getCompanyName())
                    .image(java.util.Base64.getDecoder().decode(e.getAvatar().getData()))
                    .locationName(e.getProvince() != null ? e.getProvince().getProvinceName() : "")
                    .postCount(postRepository.getAllByEmployerId(e.getId()).size())
                    .build();
            companyDTOS.add(companyDTO);
        }
        return companyDTOS;
    }

    @Override
    public CompanyDTO getCompanyById(String id) {

        Employer e = employerRepository.findEmployerById(id);

        return CompanyDTO.builder()
                .companyId(e.getId())
                .companyName(e.getCompanyName())
                .image(java.util.Base64.getDecoder().decode(e.getAvatar().getData()))
                .locationName(e.getProvince() != null ? e.getProvince().getProvinceName() : "")
                .website(e.getWebsite())
                .desc(e.getAbout())
                .postCount(postRepository.getAllByEmployerId(e.getId()).size())
                .build();
    }
}
