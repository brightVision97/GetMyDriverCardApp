package com.rachev.getmydrivercardbackend.services.base;

import com.rachev.getmydrivercardbackend.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService, BaseService<User>
{
}
