package com.jobseeking.jobseekingbe.repository;

import com.jobseeking.jobseekingbe.entity.Candidate;
import com.jobseeking.jobseekingbe.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, String> {
    Employer findEmployerById(String id);
}
