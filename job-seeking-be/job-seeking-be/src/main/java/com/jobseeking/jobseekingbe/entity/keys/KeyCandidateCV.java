package com.jobseeking.jobseekingbe.entity.keys;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KeyCandidateCV implements Serializable {
    @Column(name = "candidate_id")
    String candidateId;

    @Column(name = "cv_id")
    int cv_id;
}
