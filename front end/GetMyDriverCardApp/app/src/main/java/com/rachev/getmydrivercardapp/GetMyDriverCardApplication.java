package com.rachev.getmydrivercardapp;

import android.app.Application;
import android.content.Context;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.rachev.getmydrivercardapp.async.AsyncSchedulerProvider;
import com.rachev.getmydrivercardapp.async.base.SchedulerProvider;
import com.rachev.getmydrivercardapp.http.OkHttpHttpRequester;
import com.rachev.getmydrivercardapp.http.base.HttpRequester;
import com.rachev.getmydrivercardapp.models.BaseRequest;
import com.rachev.getmydrivercardapp.models.User;
import com.rachev.getmydrivercardapp.parsers.GsonJsonParser;
import com.rachev.getmydrivercardapp.parsers.base.JsonParser;
import com.rachev.getmydrivercardapp.repositories.HttpRequestsRepository;
import com.rachev.getmydrivercardapp.repositories.HttpUsersRepository;
import com.rachev.getmydrivercardapp.repositories.base.RequestsRepository;
import com.rachev.getmydrivercardapp.repositories.base.UserLoginRepository;
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
    private static UserLoginRepository<User> mUsersRepository;
    private static UsersService<User> mUsersService;
    private static JsonParser<BaseRequest> mRequestsJsonParser;
    private static RequestsRepository mRequestsRepository;
    private static RequestsService mRequestsService;
    private static ClearableCookieJar mCookieJar;
    
    public static SchedulerProvider getSchedulerProvider()
    {
        if (mSchedulerProvider == null)
            mSchedulerProvider = AsyncSchedulerProvider.getInstance();
        
        return mSchedulerProvider;
    }
    
    public static HttpRequester getHttpRequester(Context context)
    {
        if (mHttpRequester == null)
            mHttpRequester = new OkHttpHttpRequester(context);
        
        return mHttpRequester;
    }
    
    public static JsonParser<User> getUsersJsonParser()
    {
        if (mUsersJsonParser == null)
            mUsersJsonParser = new GsonJsonParser<>(User.class, User[].class);
        
        return mUsersJsonParser;
    }
    
    public static UserLoginRepository<User> getUsersRepository(Context context)
    {
        if (mUsersRepository == null)
            mUsersRepository = new HttpUsersRepository(
                    Constants.Strings.BASE_SERVER_URL + Constants.Strings.USERS_URL_SUFFIX,
                    getHttpRequester(context), getUsersJsonParser());
        
        return mUsersRepository;
    }
    
    public static UsersService getUsersService(Context context)
    {
        if (mUsersService == null)
            mUsersService = new HttpUsersService(context);
        
        return mUsersService;
    }
    
    public static JsonParser<BaseRequest> getRequestsJsonParser()
    {
        if (mRequestsJsonParser == null)
            mRequestsJsonParser = new GsonJsonParser<>(BaseRequest.class, BaseRequest[].class);
        
        return mRequestsJsonParser;
    }
    
    public static RequestsRepository getRequestsRepository(Context context)
    {
        if (mRequestsRepository == null)
            mRequestsRepository = new HttpRequestsRepository(
                    Constants.Strings.BASE_SERVER_URL + Constants.Strings.REQUESTS_URL_SUFFIX,
                    getHttpRequester(context), getRequestsJsonParser());
        
        return mRequestsRepository;
    }
    
    public static RequestsService getRequestsService(Context context)
    {
        if (mRequestsService == null)
            mRequestsService = new HttpRequestsService(context);
        
        return mRequestsService;
    }
    
    public static ClearableCookieJar getCookieJar(Context context)
    {
        if (mCookieJar == null)
            mCookieJar = new PersistentCookieJar(
                    new SetCookieCache(),
                    new SharedPrefsCookiePersistor(context));
        
        return mCookieJar;
    }
}
