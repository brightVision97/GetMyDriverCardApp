package com.rachev.getmydrivercardapp.services;

import com.rachev.getmydrivercardapp.GetMyDriverCardApplication;
import com.rachev.getmydrivercardapp.models.UserDTO;
import com.rachev.getmydrivercardapp.repositories.base.UsersRepository;
import com.rachev.getmydrivercardapp.services.base.UsersService;

import java.util.List;

public class HttpUsersService implements UsersService
{
    private final UsersRepository mUsersRepository;
    
    public HttpUsersService()
    {
        mUsersRepository = GetMyDriverCardApplication.getUsersRepository();
    }
    
    @Override
    public List<UserDTO> getAllUsers() throws Exception
    {
        return mUsersRepository.getAll();
    }
    
    @Override
    public UserDTO createUser(UserDTO user) throws Exception
    {
        return mUsersRepository.add(user);
    }
}
