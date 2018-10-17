package com.rachev.getmydrivercardapp;

import android.app.Application;
import com.rachev.getmydrivercardapp.async.AsyncSchedulerProvider;
import com.rachev.getmydrivercardapp.async.base.SchedulerProvider;
import com.rachev.getmydrivercardapp.http.OkHttpHttpRequester;
import com.rachev.getmydrivercardapp.http.base.HttpRequester;
import com.rachev.getmydrivercardapp.models.UserDTO;
import com.rachev.getmydrivercardapp.parsers.GsonJsonParser;
import com.rachev.getmydrivercardapp.parsers.base.JsonParser;
import com.rachev.getmydrivercardapp.repositories.HttpRepository;
import com.rachev.getmydrivercardapp.repositories.base.Repository;
import com.rachev.getmydrivercardapp.services.HttpUsersService;
import com.rachev.getmydrivercardapp.services.base.UsersService;

public class GetMyDriverCardApplication extends Application
{
    private static SchedulerProvider mSchedulerProvider;
    private static HttpRequester mHttpRequester;
    private static JsonParser<UserDTO> mJsonParser;
    private static Repository mRepository;
    private static UsersService mUsersService;
    
    public static SchedulerProvider getSchedulerProvider()
    {
        if (mSchedulerProvider == null)
            mSchedulerProvider = AsyncSchedulerProvider.getInstance();
        
        return mSchedulerProvider;
    }
    
    public static HttpRequester getHttpRequester()
    {
        if (mHttpRequester == null)
            mHttpRequester = new OkHttpHttpRequester();
        
        return mHttpRequester;
    }
    
    public static JsonParser<UserDTO> getJsonParser()
    {
        if (mJsonParser == null)
            mJsonParser = new GsonJsonParser<>(UserDTO.class, UserDTO[].class);
        
        return mJsonParser;
    }
    
    public static Repository getUsersRepository()
    {
        if (mRepository == null)
            mRepository = new HttpRepository();
        
        return mRepository;
    }
    
    public static UsersService getUsersService()
    {
        if (mUsersService == null)
            mUsersService = new HttpUsersService();
        
        return mUsersService;
    }
}
