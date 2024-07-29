package com.jobseeking.jobseekingbe.entity;

import com.jobseeking.jobseekingbe.entity.keys.KeyCandidateCV;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "candidate_cv")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandidateCV {

    @EmbeddedId
    KeyCandidateCV keyCandidateCV;

    @ManyToOne
    @JoinColumn(name = "candidate_id", insertable=false, updatable=false)
    Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "cv_id", insertable=false, updatable=false)
    CV cv;
}
