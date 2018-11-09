package com.rachev.getmydrivercardapp;

import android.app.Application;
import com.rachev.getmydrivercardapp.async.AsyncSchedulerProvider;
import com.rachev.getmydrivercardapp.async.base.SchedulerProvider;
import com.rachev.getmydrivercardapp.http.OkHttpHttpRequester;
import com.rachev.getmydrivercardapp.http.base.HttpRequester;
import com.rachev.getmydrivercardapp.models.User;
import com.rachev.getmydrivercardapp.models.base.BaseRequest;
import com.rachev.getmydrivercardapp.parsers.GsonJsonParser;
import com.rachev.getmydrivercardapp.parsers.base.JsonParser;
import com.rachev.getmydrivercardapp.repositories.HttpRequestsRepository;
import com.rachev.getmydrivercardapp.repositories.HttpUsersRepository;
import com.rachev.getmydrivercardapp.repositories.base.Repository;
import com.rachev.getmydrivercardapp.repositories.base.RequestsRepository;
import com.rachev.getmydrivercardapp.services.HttpRequestsService;
import com.rachev.getmydrivercardapp.services.HttpUsersService;
import com.rachev.getmydrivercardapp.services.base.RequestsService;
import com.rachev.getmydrivercardapp.services.base.UsersService;
import com.rachev.getmydrivercardapp.utils.Constants;

public class GetMyDriverCardApplication extends Application
{
    private static SchedulerProvider mSchedulerProvider;
    private static HttpRequester mHttpRequester;
    private static JsonParser<User> mUsersJsonParser;
    private static Repository<User> mUsersRepository;
    private static UsersService mUsersService;
    private static JsonParser<BaseRequest> mRequestsJsonParser;
    private static RequestsRepository mRequestsRepository;
    private static RequestsService mRequestsService;
    
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
    
    public static JsonParser<User> getUsersJsonParser()
    {
        if (mUsersJsonParser == null)
            mUsersJsonParser = new GsonJsonParser<>(User.class, User[].class);
        
        return mUsersJsonParser;
    }
    
    public static Repository<User> getUsersRepository()
    {
        if (mUsersRepository == null)
            mUsersRepository = new HttpUsersRepository(
                    Constants.Strings.BASE_SERVER_URL + Constants.Strings.USERS_URL_SUFFIX,
                    getHttpRequester(), getUsersJsonParser());
        
        return mUsersRepository;
    }
    
    public static UsersService getUsersService()
    {
        if (mUsersService == null)
            mUsersService = new HttpUsersService(getUsersRepository());
        
        return mUsersService;
    }
    
    public static JsonParser<BaseRequest> getRequestsJsonParser()
    {
        if (mRequestsJsonParser == null)
            mRequestsJsonParser = new GsonJsonParser<>(BaseRequest.class, BaseRequest[].class);
        
        return mRequestsJsonParser;
    }
    
    public static RequestsRepository getRequestsRepository()
    {
        if (mRequestsRepository == null)
            mRequestsRepository = new HttpRequestsRepository(
                    Constants.Strings.BASE_SERVER_URL + Constants.Strings.REQUESTS_URL_SUFFIX,
                    getHttpRequester(), getRequestsJsonParser());
        
        return mRequestsRepository;
    }
    
    
    public static RequestsService getRequestsService()
    {
        if (mRequestsService == null)
            mRequestsService = new HttpRequestsService(getRequestsRepository());
        
        return mRequestsService;
    }
}
