package com.rachev.getmydrivercardbackend.services.base;

import com.rachev.getmydrivercardbackend.models.UserDTO;

import java.util.List;

public interface UsersService
{
    List<UserDTO> getAllUsers();

    UserDTO getByUsername(String username);

    void createUser(UserDTO userDTO);
}
