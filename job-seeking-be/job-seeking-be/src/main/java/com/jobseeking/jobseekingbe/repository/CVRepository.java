package com.jobseeking.jobseekingbe.repository;

import com.jobseeking.jobseekingbe.entity.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CVRepository extends JpaRepository<CV, Integer> {


}
