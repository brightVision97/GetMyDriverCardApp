package com.rachev.getmydrivercardapp.services.base;

import com.rachev.getmydrivercardapp.models.UserDTO;

import java.util.List;

public interface UsersService
{
    List<UserDTO> getAllUsers() throws Exception;
    
    UserDTO createUser(UserDTO user) throws Exception;
}
