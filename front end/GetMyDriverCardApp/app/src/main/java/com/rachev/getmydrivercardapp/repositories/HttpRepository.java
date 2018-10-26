package com.rachev.getmydrivercardapp.repositories;

import com.rachev.getmydrivercardapp.http.base.HttpRequester;
import com.rachev.getmydrivercardapp.parsers.base.JsonParser;
import com.rachev.getmydrivercardapp.repositories.base.Repository;
import com.rachev.getmydrivercardapp.utils.Constants;

import java.io.IOException;
import java.util.List;

public class HttpRepository<T> implements Repository<T>
{
    private final HttpRequester mHttpRequester;
    private final String mServerUrl;
    private final JsonParser<T> mJsonParser;
    
    public HttpRepository(String serverUrl, HttpRequester httpRequester, JsonParser<T> jsonParser)
    {
        mServerUrl = serverUrl;
        mHttpRequester = httpRequester;
        mJsonParser = jsonParser;
    }
    
    @Override
    public List<T> getAll() throws IOException
    {
        String usersJson = mHttpRequester.get(mServerUrl);
        
        return mJsonParser.fromJsonArray(usersJson);
    }
    
    @Override
    public T getByUsername(String username) throws Exception
    {
        String url = mServerUrl + "/" + username;
        String json = mHttpRequester.get(url);
        
        return mJsonParser.fromJson(json);
    }
    
    @Override
    public T add(T item) throws Exception
    {
        String requestBody = mJsonParser.toJson(item);
        String responseBody = mHttpRequester.post(mServerUrl, requestBody);
        
        if (responseBody == null)
            throw new IllegalArgumentException(Constants.USER_CANT_SIGNUP_TOAST);
        
        return mJsonParser.fromJson(responseBody);
    }
}
