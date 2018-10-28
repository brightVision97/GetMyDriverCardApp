package com.rachev.getmydrivercardapp.services;

import com.rachev.getmydrivercardapp.models.User;
import com.rachev.getmydrivercardapp.repositories.base.Repository;
import com.rachev.getmydrivercardapp.services.base.UsersService;

import java.util.List;

public class HttpUsersService implements UsersService
{
    private final Repository<User> mRepository;
    
    public HttpUsersService(Repository<User> repository)
    {
        mRepository = repository;
    }
    
    @Override
    public List<User> getAllUsers() throws Exception
    {
        return mRepository.getAll();
    }
    
    @Override
    public User getByUsername(String username) throws Exception
    {
        return mRepository.getByUsername(username);
    }
    
    @Override
    public User createUser(User user) throws Exception
    {
        return mRepository.add(user);
    }
}
