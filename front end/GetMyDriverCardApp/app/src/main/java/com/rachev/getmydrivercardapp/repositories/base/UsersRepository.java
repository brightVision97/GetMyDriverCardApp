package com.rachev.getmydrivercardapp.repositories.base;

import com.rachev.getmydrivercardapp.models.UserDTO;

import java.io.IOException;
import java.util.List;

public interface UsersRepository
{
    List<UserDTO> getAll() throws IOException;
    
    UserDTO getById(int id) throws IOException;
    
    UserDTO add(UserDTO item) throws IOException;
}
