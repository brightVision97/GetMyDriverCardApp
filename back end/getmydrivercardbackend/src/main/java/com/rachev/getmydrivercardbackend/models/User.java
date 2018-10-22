package com.rachev.getmydrivercardbackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @Nullable
    @Column(name = "login_type", unique = true)
    private String loginType;

    @Nullable
    @Column(name = "email", unique = true)
    private String email;

    @Nullable
    @Column(name = "password", unique = true)
    private String password;

    @NotNull
    @Column(name = "social_id", unique = true)
    private String socialId;

    @NotNull
    @Column(name = "role", unique = true)
    private String role;
}
