package com.rachev.getmydrivercardapp.services.base;

import java.io.IOException;

public interface UsersService<User> extends BaseService<User>
{
    User login(String username, String password) throws IOException;
}
