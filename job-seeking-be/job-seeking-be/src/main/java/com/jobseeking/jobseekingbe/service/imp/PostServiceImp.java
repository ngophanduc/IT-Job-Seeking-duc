package com.jobseeking.jobseekingbe.service.imp;

import com.jobseeking.jobseekingbe.dto.ApiResponse;
import com.jobseeking.jobseekingbe.dto.request.PostCreationRequest;
import com.jobseeking.jobseekingbe.dto.request.PostFilterRequest;
import com.jobseeking.jobseekingbe.dto.request.PostUpdateRequest;
import com.jobseeking.jobseekingbe.dto.response.*;
import com.jobseeking.jobseekingbe.entity.Employer;
import com.jobseeking.jobseekingbe.entity.Post;

import java.util.List;

public interface PostServiceImp {

    int savePost(Post post);

    boolean updatePost(int id, PostUpdateRequest postUpdateRequest);
    List<PostDTO> getAllActivePost();
    PostDTO getPostById(int id);
    List<PostDTO> getAllPendingPosts();

    List<PostDTO> getAllPost();

    List<PostDTO> getPostByStatus(int id);

    boolean activePost(int postId);
    boolean rejectPost(int postId);

    List<PostDTO> getAllPostByEmployerId(String id);
    AuthenticationResponse addPost(PostCreationRequest postCreationRequest);

    boolean addPostWhenAuthenticated(String id, PostCreationRequest postCreationRequest);

    void insertPost(Employer employer, PostCreationRequest postCreationRequest);

    boolean updatePostLevelDetail(int postId, String [] listLevelId);
    boolean updatePostTypeDetail(int postId, String [] listTypeId);
    boolean updatePostContractDetail(int postId, String [] listContractId);

    void updatePostRequirement(int postId, String title, String [] requirement);

    List<PostLevelDTO> getPostLevels();
    List<PostTypeDTO> getPostTypes();
    List<PostContractDTO> getPostContracts();

    PostRequirementDTO getPostRequirement(int postId);

    PostDTO postMapper(Post post);
    List<PostDTO> filterPost(PostFilterRequest postFilterRequest);
}
