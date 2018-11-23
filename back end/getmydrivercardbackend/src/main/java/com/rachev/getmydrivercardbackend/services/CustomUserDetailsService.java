package com.rachev.getmydrivercardbackend.services;

import com.rachev.getmydrivercardbackend.models.CustomUserDetails;
import com.rachev.getmydrivercardbackend.models.User;
import com.rachev.getmydrivercardbackend.repositories.RoleRepository;
import com.rachev.getmydrivercardbackend.repositories.UsersRepository;
import com.rachev.getmydrivercardbackend.services.base.UsersService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
public class CustomUserDetailsService implements UsersService
{
    private static final int USER_ROLE_ID = 2;
    
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    
    @Autowired
    public CustomUserDetailsService(UsersRepository usersRepository,
                                    RoleRepository roleRepository)
    {
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
    }
    
    @Override
    public List<User> getAll()
    {
        return usersRepository.findAll();
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return usersRepository.findByUsername(username)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Incorrect email or password"));
    }
    
    @Override
    public User getById(int userId)
    {
        return usersRepository.findById(userId)
                .orElse(null);
    }
    
    @Override
    public User add(User user)
    {
        roleRepository.findById(USER_ROLE_ID)
                .ifPresent(role -> user.setRoles(new HashSet<>(Collections.singletonList(role))));
        
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        if (user.getPassword() != null)
            user.setPassword(bcrypt.encode(user.getPassword()));
        
        return usersRepository.save(user);
    }
}
