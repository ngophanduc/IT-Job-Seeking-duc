package com.jobseeking.jobseekingbe.entity;

import com.jobseeking.jobseekingbe.entity.keys.KeyEmployerCandidate;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity(name = "company_follow")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployerFollow {

    @EmbeddedId
    KeyEmployerCandidate keyEmployerCandidate;

    @ManyToOne
    @JoinColumn(name = "candidate_id", insertable=false, updatable=false)
    Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "employer_id", insertable=false, updatable=false)
    Employer employer;
}
