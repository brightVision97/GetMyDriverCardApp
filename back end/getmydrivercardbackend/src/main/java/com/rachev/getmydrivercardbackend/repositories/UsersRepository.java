package com.rachev.getmydrivercardbackend.repositories;

import com.rachev.getmydrivercardbackend.models.UserDTO;
import java.util.List;

public interface UsersRepository {
    
    void create(UserDTO user);

    List<UserDTO> getAll();
}
