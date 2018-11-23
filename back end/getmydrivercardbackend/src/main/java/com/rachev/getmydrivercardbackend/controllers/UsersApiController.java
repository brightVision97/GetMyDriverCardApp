package com.rachev.getmydrivercardbackend.controllers;

import com.rachev.getmydrivercardbackend.models.User;
import com.rachev.getmydrivercardbackend.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersApiController
{
    private final CustomUserDetailsService usersService;
    
    @Autowired
    public UsersApiController(CustomUserDetailsService usersService)
    {
        this.usersService = usersService;
    }
    
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/secured/getAll")
    public List<User> getAllUsers()
    {
        return usersService.getAll();
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/get/{userId}")
    public User getUserById(@PathVariable int userId)
    {
        return usersService.getById(userId);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/me")
    @ResponseBody
    public User getCurrentUser(Principal principal)
    {
        return (User) usersService.loadUserByUsername(principal.getName());
    }
    
    @ResponseBody
    @PostMapping("/signup")
    public User createUser(@RequestBody User user)
    {
        return usersService.add(user);
    }
}
