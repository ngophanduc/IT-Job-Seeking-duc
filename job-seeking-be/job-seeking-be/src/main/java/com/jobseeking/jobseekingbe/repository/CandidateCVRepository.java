package com.jobseeking.jobseekingbe.repository;

import com.jobseeking.jobseekingbe.entity.CandidateCV;
import com.jobseeking.jobseekingbe.entity.keys.KeyCandidateCV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateCVRepository extends JpaRepository<CandidateCV, KeyCandidateCV> {

    List<CandidateCV> findAllByKeyCandidateCVCandidateId(String id);
}
