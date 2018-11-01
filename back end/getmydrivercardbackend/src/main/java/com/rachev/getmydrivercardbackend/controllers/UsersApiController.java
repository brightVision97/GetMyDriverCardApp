package com.rachev.getmydrivercardbackend.controllers;

import com.rachev.getmydrivercardbackend.models.User;
import com.rachev.getmydrivercardbackend.services.base.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersApiController
{
    private final UsersService usersService;
    
    @Autowired
    public UsersApiController(UsersService usersService)
    {
        this.usersService = usersService;
    }
    
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/secured")
    public List<User> getAllUsers()
    {
        return usersService.getAllUsers();
    }
    
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/secured/{username}")
    public User getByUsername(@PathVariable String username)
    {
        return (User) usersService.loadUserByUsername(username);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/me")
    public @ResponseBody
    User getCurrentUser(Principal principal)
    {
        return (User) usersService.loadUserByUsername(principal.getName());
    }
    
    @ResponseBody
    @PostMapping("/signup")
    public User createUser(@RequestBody User user)
    {
        return usersService.createUser(user);
    }
}
