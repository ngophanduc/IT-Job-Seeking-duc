package com.jobseeking.jobseekingbe.repository;

import com.jobseeking.jobseekingbe.entity.PostTypeDetail;
import com.jobseeking.jobseekingbe.entity.keys.KeyPostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PostTypeDetailRepository extends JpaRepository<PostTypeDetail, KeyPostType> {
    Set<PostTypeDetail> findAllByKeyPostTypePostId(int postId);
    void deleteAllByKeyPostTypePostId(int postId);
}
