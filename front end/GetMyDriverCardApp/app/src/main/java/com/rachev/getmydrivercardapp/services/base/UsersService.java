package com.rachev.getmydrivercardapp.services.base;

import com.rachev.getmydrivercardapp.models.User;

import java.util.List;

public interface UsersService
{
    List<User> getAllUsers() throws Exception;
    
    User getByUsername(String username) throws Exception;
    
    User createUser(User user) throws Exception;
}
