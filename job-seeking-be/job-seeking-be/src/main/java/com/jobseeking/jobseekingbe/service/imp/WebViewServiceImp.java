package com.jobseeking.jobseekingbe.service.imp;

import com.jobseeking.jobseekingbe.dto.response.*;

import java.util.List;

public interface WebViewServiceImp {
    List<ProvinceDTO> getListProvince();

    List<PostStatusDTO> getPostStatus();

    List<CompanyDTO> getAllEmployers();

    CompanyDTO getCompanyById(String id);


}
