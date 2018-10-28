package com.rachev.getmydrivercardbackend.repositories.base;

import com.rachev.getmydrivercardbackend.models.User;

import java.util.List;

public interface UsersRepository
{
    List<User> getAll();
    
    User getByUsername(String username);
    
    void create(User user);
}
