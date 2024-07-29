package com.jobseeking.jobseekingbe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity(name = "cv")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int cvId;

    @Column(name = "name")
    String cvName;

    @Lob
    @Column(name = "data")
    String data;

    @OneToMany(mappedBy = "cv")
    Set<CandidateCV> candidateCVSet;
}
