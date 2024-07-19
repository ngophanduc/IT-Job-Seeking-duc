package com.jobseeking.jobseekingbe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    String id;

    @Column(name = "email")
    String email;

    @Column(name = "password")
    String password;

    @Column(name = "phone")
    String phone;

    @Column(name = "blocked")
    boolean blocked = false;

    public User(String email, String password, String phone, Role role) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;

    @OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn
    Avatar avatar;
}
