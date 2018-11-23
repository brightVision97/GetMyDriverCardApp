package com.rachev.getmydrivercardapp.services;

import android.content.Context;
import com.rachev.getmydrivercardapp.GetMyDriverCardApplication;
import com.rachev.getmydrivercardapp.models.User;
import com.rachev.getmydrivercardapp.repositories.base.UserLoginRepository;
import com.rachev.getmydrivercardapp.services.base.UsersService;

import java.io.IOException;
import java.util.List;

public class HttpUsersService implements UsersService<User>
{
    private final UserLoginRepository<User> mUsersRepository;
    
    public HttpUsersService(Context context)
    {
        mUsersRepository = GetMyDriverCardApplication.getUsersRepository(context);
    }
    
    @Override
    public List<User> getAll() throws Exception
    {
        return mUsersRepository.getAll();
    }
    
    @Override
    public User create(User user) throws Exception
    {
        return mUsersRepository.add(user);
    }
    
    @Override
    public User login(String username, String password) throws IOException
    {
        return mUsersRepository.login(username, password);
    }
}
