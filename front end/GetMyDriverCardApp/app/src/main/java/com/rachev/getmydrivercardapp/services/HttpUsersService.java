package com.rachev.getmydrivercardapp.services;

import com.rachev.getmydrivercardapp.models.UserDTO;
import com.rachev.getmydrivercardapp.repositories.base.Repository;
import com.rachev.getmydrivercardapp.services.base.UsersService;

import java.util.List;

public class HttpUsersService implements UsersService
{
    private final Repository<UserDTO> mRepository;
    
    public HttpUsersService(Repository<UserDTO> repository)
    {
        mRepository = repository;
    }
    
    @Override
    public List<UserDTO> getAllUsers() throws Exception
    {
        return mRepository.getAll();
    }
    
    @Override
    public UserDTO getByUsername(String username) throws Exception
    {
        return mRepository.getByUsername(username);
    }
    
    @Override
    public UserDTO createUser(UserDTO user) throws Exception
    {
        return mRepository.add(user);
    }
}
