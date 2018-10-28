package com.rachev.getmydrivercardapp;

import android.app.Application;
import com.rachev.getmydrivercardapp.async.AsyncSchedulerProvider;
import com.rachev.getmydrivercardapp.async.base.SchedulerProvider;
import com.rachev.getmydrivercardapp.http.OkHttpHttpRequester;
import com.rachev.getmydrivercardapp.http.base.HttpRequester;
import com.rachev.getmydrivercardapp.models.User;
import com.rachev.getmydrivercardapp.parsers.GsonJsonParser;
import com.rachev.getmydrivercardapp.parsers.base.JsonParser;
import com.rachev.getmydrivercardapp.repositories.HttpRepository;
import com.rachev.getmydrivercardapp.repositories.base.Repository;
import com.rachev.getmydrivercardapp.services.HttpUsersService;
import com.rachev.getmydrivercardapp.services.base.UsersService;
import com.rachev.getmydrivercardapp.utils.Constants;

public class GetMyDriverCardApplication extends Application
{
    private static SchedulerProvider mSchedulerProvider;
    private static HttpRequester mHttpRequester;
    private static JsonParser<User> mJsonParser;
    private static Repository<User> mRepository;
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
            mHttpRequester = OkHttpHttpRequester.getInstance();
        
        return mHttpRequester;
    }
    
    public static JsonParser<User> getJsonParser()
    {
        if (mJsonParser == null)
            mJsonParser = new GsonJsonParser<>(User.class, User[].class);
        
        return mJsonParser;
    }
    
    public static Repository<User> getUsersRepository()
    {
        if (mRepository == null)
            mRepository = new HttpRepository(
                    Constants.Strings.BASE_SERVER_URL
                            + Constants.Strings.USERS_URL_SUFFIX,
                    getHttpRequester(), getJsonParser());
        
        return mRepository;
    }
    
    public static UsersService getUsersService()
    {
        if (mUsersService == null)
            mUsersService = new HttpUsersService(getUsersRepository());
        
        return mUsersService;
    }
}
