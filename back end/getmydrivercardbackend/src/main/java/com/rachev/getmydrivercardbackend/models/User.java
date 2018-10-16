package com.rachev.getmydrivercardbackend.models;

import jdk.internal.jline.internal.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @NotNull
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
