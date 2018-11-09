package com.rachev.getmydrivercardbackend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true)
    private int id;
    
    @Nullable
    @Column(name = "username", unique = true)
    private String username;
    
    @Nullable
    @Column(name = "password")
    private String password;
    
    @Nullable
    @Column(name = "facebook_id")
    private String facebookId;
    
    @Nullable
    @Column(name = "google_id")
    private String googleId;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    
    User(User user)
    {
        setId(user.getId());
        setUsername(user.getUsername());
        setPassword(user.getPassword());
        setRoles(user.getRoles());
        setGoogleId(user.getGoogleId());
        setFacebookId(user.getFacebookId());
    }
}
