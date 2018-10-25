package com.rachev.getmydrivercardbackend.controllers;

import com.rachev.getmydrivercardbackend.models.UserDTO;
import com.rachev.getmydrivercardbackend.services.base.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserApiController
{
    private final UsersService usersService;
    
    @Autowired
    public UserApiController(UsersService usersService)
    {
        this.usersService = usersService;
    }
    
    @GetMapping
    public List<UserDTO> getAllUsers()
    {
        return usersService.getAllUsers();
    }
    
    @GetMapping("/{username}")
    public UserDTO getByUsername(@PathVariable String username)
    {
        return usersService.getByUsername(username);
    }
    
    @PostMapping
    public void createUser(@RequestBody UserDTO userDTO)
    {
        usersService.createUser(userDTO);
    }
}
