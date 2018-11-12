package com.rachev.getmydrivercardapp.repositories.base;

import java.io.IOException;

public interface UserLoginRepository<T> extends BaseRepository<T>
{
    T login(String username, String password) throws IOException;
}
