package com.rachev.getmydrivercardbackend.repositories;

import com.rachev.getmydrivercardbackend.models.UserDTO;
import java.util.List;

public interface UsersRepository {
    
    void create(UserDTO user);

    UserDTO getByEmail(String email);

    List<UserDTO> getAll();
}
