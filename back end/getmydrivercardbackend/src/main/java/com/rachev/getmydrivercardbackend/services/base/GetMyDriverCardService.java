package com.rachev.getmydrivercardbackend.services.base;

import com.rachev.getmydrivercardbackend.models.UserDTO;

import java.util.List;

public interface GetMyDriverCardService {
    List<UserDTO> getAllUsers();

    UserDTO getByEmail(String email);

    void createUser(UserDTO userDTO);
}
