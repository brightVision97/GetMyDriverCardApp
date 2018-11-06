package com.rachev.getmydrivercardapp.services;

import com.rachev.getmydrivercardapp.models.User;
import com.rachev.getmydrivercardapp.repositories.base.Repository;
import com.rachev.getmydrivercardapp.services.base.UsersService;

import java.util.List;

public class HttpUsersService implements UsersService
{
    private final Repository<User> mUsersRepository;
    
    public HttpUsersService(Repository<User> usersRepository)
    {
        mUsersRepository = usersRepository;
    }
    
    @Override
    public List<User> getAllUsers() throws Exception
    {
        return mUsersRepository.getAll();
    }
    
    @Override
    public User createUser(User user) throws Exception
    {
        return mUsersRepository.add(user);
    }
}
