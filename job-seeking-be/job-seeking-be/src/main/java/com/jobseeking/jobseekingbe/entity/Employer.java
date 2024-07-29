package com.jobseeking.jobseekingbe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Entity(name = "employers")
@Data
@PrimaryKeyJoinColumn(name="user_id")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employer extends User {

    @Column(name = "emp_name")
    String companyName;

    @Lob
    @Column(name = "background")
    String background;

    @Column(name = "website")
    String website;

    @Column(name = "about_us")
    String about;

    @Column(name = "address")
    String address;

    @Column(name = "facebook")
    String facebook;

    @Column(name = "linkedin")
    String linkedin;

    @Builder
    public Employer(String email, String password, String phone, Role role, String companyName, String background, String website, String about, String address, String facebook, String linkedin, Set<Post> posts, Province province) {
        super(email, password, phone, role);
        this.companyName = companyName;
        this.background = background;
        this.website = website;
        this.about = about;
        this.address = address;
        this.facebook = facebook;
        this.linkedin = linkedin;
        this.posts = posts;
        this.province = province;
    }

    @OneToMany(mappedBy = "employer")
    Set<Post> posts;

    @OneToMany(mappedBy = "employer")
    Set<EmployerFollow> employerFollows;

    @ManyToOne
    @JoinColumn(name = "pro_id")
    Province province;
}
