package com.jobseeking.jobseekingbe.repository;

import com.jobseeking.jobseekingbe.entity.EmployerFollow;
import com.jobseeking.jobseekingbe.entity.PostSave;
import com.jobseeking.jobseekingbe.entity.keys.KeyEmployerCandidate;
import com.jobseeking.jobseekingbe.entity.keys.KeyPostCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployerFollowRepository extends JpaRepository<EmployerFollow, KeyEmployerCandidate> {
    List<EmployerFollow> findAllByKeyEmployerCandidateCandidateId(String userId);
    void deleteByKeyEmployerCandidate(KeyEmployerCandidate keyEmployerCandidate);
}
