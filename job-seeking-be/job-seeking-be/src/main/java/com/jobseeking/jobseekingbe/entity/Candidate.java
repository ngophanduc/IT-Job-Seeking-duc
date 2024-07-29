package com.jobseeking.jobseekingbe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity(name = "candidates")
@Data
@PrimaryKeyJoinColumn(name="user_id")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Candidate extends User {

    @Column(name = "fullname")
    String fullName;

    @Column(name = "dob")
    Date dob;

    @Column(name = "position")
    String position;

    @Column(name = "current_salary")
    String currentSalary;

    @Column(name = "self_desc")
    String selfDesc;

    @Column(name = "gender")
    String gender;

    @Column(name = "age")
    String age;

    @Column(name = "salary_expect")
    String salaryExpect;

    @Column(name = "facebook")
    String facebook;

    @Column(name = "linkedin")
    String linkedin;

    @Builder
    public Candidate(String email, String password, String phone, Role role, String fullName, Date dob, String position, String currentSalary, String selfDesc, String gender, String age, String salaryExpect, String facebook, String linkedin, Province province) {
        super(email, password, phone, role);
        this.fullName = fullName;
        this.dob = dob;
        this.position = position;
        this.currentSalary = currentSalary;
        this.selfDesc = selfDesc;
        this.gender = gender;
        this.age = age;
        this.salaryExpect = salaryExpect;
        this.facebook = facebook;
        this.linkedin = linkedin;
        this.province = province;
    }

    @ManyToOne
    @JoinColumn(name = "pro_id")
    Province province;

    @OneToMany(mappedBy = "candidate")
    Set<PostSave> postSaves;

    @OneToMany(mappedBy = "candidate")
    Set<EmployerFollow> employerFollows;

    @OneToMany(mappedBy = "candidate")
    Set<CandidateCV> candidateCVSet;
}
