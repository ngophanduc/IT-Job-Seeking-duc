package com.jobseeking.jobseekingbe.repository;

import com.jobseeking.jobseekingbe.entity.CandidateSave;
import com.jobseeking.jobseekingbe.entity.keys.KeyEmployerCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateSaveRepository extends JpaRepository<CandidateSave, KeyEmployerCandidate> {
}
