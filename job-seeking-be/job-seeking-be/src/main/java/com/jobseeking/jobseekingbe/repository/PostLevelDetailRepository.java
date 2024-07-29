package com.jobseeking.jobseekingbe.repository;

import com.jobseeking.jobseekingbe.entity.PostLevel;
import com.jobseeking.jobseekingbe.entity.PostLevelDetail;
import com.jobseeking.jobseekingbe.entity.keys.KeyPostLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository

public interface PostLevelDetailRepository extends JpaRepository<PostLevelDetail, KeyPostLevel> {
    Set<PostLevelDetail> findAllByKeyPostLevelPostId(int postId);
    void deleteAllByKeyPostLevelPostId(int postId);
}
