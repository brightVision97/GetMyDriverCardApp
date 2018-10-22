package com.rachev.getmydrivercardbackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private int id;
    private String loginType;
    private String email;
    private String password;
    private String sodicalId;
    private  String role;
}
