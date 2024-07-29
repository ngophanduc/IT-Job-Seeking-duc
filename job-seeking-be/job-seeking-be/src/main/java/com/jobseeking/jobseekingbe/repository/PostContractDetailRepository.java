package com.jobseeking.jobseekingbe.repository;

import com.jobseeking.jobseekingbe.entity.PostContractDetail;
import com.jobseeking.jobseekingbe.entity.keys.KeyPostContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository

public interface PostContractDetailRepository extends JpaRepository<PostContractDetail, KeyPostContract> {
    Set<PostContractDetail> findAllByKeyPostContractPostId(int postId);
    void deleteAllByKeyPostContractPostId(int postId);
}
