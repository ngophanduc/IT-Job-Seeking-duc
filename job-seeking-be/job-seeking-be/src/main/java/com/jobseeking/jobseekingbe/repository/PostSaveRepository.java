package com.jobseeking.jobseekingbe.repository;

import com.jobseeking.jobseekingbe.entity.Post;
import com.jobseeking.jobseekingbe.entity.PostSave;
import com.jobseeking.jobseekingbe.entity.keys.KeyPostCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostSaveRepository extends JpaRepository<PostSave, KeyPostCandidate> {

    List<PostSave> findAllByKeyPostCandidateUserId(String userId);

    void deleteByKeyPostCandidate(KeyPostCandidate keyPostCandidate);
}
