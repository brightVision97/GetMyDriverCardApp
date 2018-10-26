package com.rachev.getmydrivercardbackend.services;

import com.rachev.getmydrivercardbackend.models.dtos.UserDTO;
import com.rachev.getmydrivercardbackend.repositories.base.UsersRepository;
import com.rachev.getmydrivercardbackend.services.base.UsersService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService
{
    private final UsersRepository usersRepository;
    
    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository)
    {
        this.usersRepository = usersRepository;
    }
    
    @Override
    public List<UserDTO> getAllUsers()
    {
        return usersRepository.getAll();
    }
    
    @Override
    public UserDTO getByUsername(String username)
    {
        return usersRepository.getByUsername(username);
    }
    
    @Override
    public void createUser(UserDTO userDTO)
    {
        usersRepository.create(userDTO);
    }
}
