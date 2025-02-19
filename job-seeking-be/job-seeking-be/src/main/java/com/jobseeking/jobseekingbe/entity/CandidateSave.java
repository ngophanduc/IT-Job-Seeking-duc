package com.jobseeking.jobseekingbe.entity;

import com.jobseeking.jobseekingbe.entity.keys.KeyEmployerCandidate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity(name = "candidate_save")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(KeyEmployerCandidate.class)
public class CandidateSave {

    @Id
    @Column(name = "candidate_id")
    String candidateId;

    @Id
    @Column(name = "employer_id")
    String employerId;
}
