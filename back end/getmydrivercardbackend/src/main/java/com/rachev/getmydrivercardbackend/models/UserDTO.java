package com.rachev.getmydrivercardbackend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class UserDTO
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;
    
    @Nullable
    @Column(name = "username", unique = true)
    private String username;
    
    @Nullable
    @Column(name = "password")
    private String password;
    
    @Nullable
    @Column(name = "facebook_id")
    private String facebookid;
    
    @Nullable
    @Column(name = "google_id")
    private String googleId;
    
    @NotNull
    @Column(name = "role")
    private String role;
    
    public UserDTO(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
}
