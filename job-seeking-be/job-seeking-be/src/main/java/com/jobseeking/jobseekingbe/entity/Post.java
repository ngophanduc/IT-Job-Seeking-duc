package com.jobseeking.jobseekingbe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Entity(name = "post")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int postId;

    @Column(name = "job_title")
    String jobTitle;

    @Column(name = "job_desc")
    String jobDesc;

    @Column(name = "min_salary")
    Long minSalary;

    @Column(name = "max_salary")
    Long maxSalary;

    @Column(name = "end_date")
    Date endDate;

    @Column(name = "tech")
    String technologies;

    @ManyToOne
    @JoinColumn(name = "status_id")
    PostStatus postStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    Employer employer;

    @OneToMany(mappedBy = "post")
    Set<PostRequirement> postRequirements;

    @OneToMany(mappedBy = "post")
    Set<PostLevelDetail> postLevelDetails;

    @OneToMany(mappedBy = "post")
    Set<PostTypeDetail> postTypeDetails;

    @OneToMany(mappedBy = "post")
    Set<PostContractDetail> postContractDetails;

    @OneToMany(mappedBy = "post")
    Set<PostSave> postSaves;

}
