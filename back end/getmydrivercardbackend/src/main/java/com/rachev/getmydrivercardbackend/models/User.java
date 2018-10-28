package com.rachev.getmydrivercardbackend.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
@RequiredArgsConstructor
@Getter
@Setter
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;
    
    @Nullable
    @Column(name = "username", unique = true)
    private final String username;
    
    @Nullable
    @Column(name = "password")
    private final String password;
    
    @Nullable
    @Column(name = "facebook_id")
    private String facebookid;
    
    @Nullable
    @Column(name = "google_id")
    private String googleId;
    
    @NotNull
    @Column(name = "role")
    private String role;
    
    public User()
    {
        username = null;
        password = null;
    }
}
