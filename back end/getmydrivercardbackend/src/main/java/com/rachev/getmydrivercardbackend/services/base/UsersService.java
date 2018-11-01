package com.rachev.getmydrivercardbackend.services.base;

import com.rachev.getmydrivercardbackend.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsersService extends UserDetailsService
{
    List<User> getAllUsers();
    
    User createUser(User user);
}
