package com.rachev.getmydrivercardbackend.repositories;

import com.rachev.getmydrivercardbackend.models.User;

import java.util.List;


public interface UsersRepository {
    void create(User user);

    List<User> getAll();


}
