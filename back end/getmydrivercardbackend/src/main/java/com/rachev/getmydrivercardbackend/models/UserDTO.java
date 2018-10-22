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
public class UserDTO {
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
    @Column(name = "password")
    private String password;

    @Nullable
    @Column(name = "social_id", unique = true)
    private String socialId;

    @NotNull
    @Column(name = "role")
    private String role;

    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
