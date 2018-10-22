package com.rachev.getmydrivercardbackend.services.base;

import com.rachev.getmydrivercardbackend.models.User;

import java.util.List;

public interface GetMyDriverCardService {
    List<User> getAllUsers();

    void createUser(User user);
}
