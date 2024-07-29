package com.jobseeking.jobseekingbe.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CompanyDTO {

    String companyId;
    String companyName;
    String desc;
    String website;
    byte[] image;
    String locationName;
    int postCount;
    List<PostDTO> postDTOS;
}
