package com.rachev.getmydrivercardbackend.services.base;

import com.rachev.getmydrivercardbackend.models.User;

import java.util.List;

public interface UsersService
{
    List<User> getAllUsers();

    User getByUsername(String username);

    void createUser(User user);
}
