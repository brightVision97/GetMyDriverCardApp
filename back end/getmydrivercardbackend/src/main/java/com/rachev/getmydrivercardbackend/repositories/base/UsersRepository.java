package com.rachev.getmydrivercardbackend.repositories.base;

import com.rachev.getmydrivercardbackend.models.UserDTO;

import java.util.List;

public interface UsersRepository
{
    List<UserDTO> getAll();
    
    UserDTO getByUsername(String username);
    
    void create(UserDTO user);
}
