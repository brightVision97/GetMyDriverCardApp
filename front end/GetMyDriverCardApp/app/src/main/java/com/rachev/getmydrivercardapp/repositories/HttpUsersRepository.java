package com.rachev.getmydrivercardapp.repositories;

import com.rachev.getmydrivercardapp.http.base.HttpRequester;
import com.rachev.getmydrivercardapp.parsers.base.JsonParser;
import com.rachev.getmydrivercardapp.repositories.base.UserLoginRepository;

import java.io.IOException;
import java.util.List;

public class HttpUsersRepository<T> implements UserLoginRepository<T>
{
    private final HttpRequester mHttpRequester;
    private final String mUsersEndpointUrl;
    private final JsonParser<T> mUsersJsonParser;
    
    public HttpUsersRepository(String usersEndpointUrl, HttpRequester httpRequester,
                               JsonParser<T> jsonParser)
    {
        mUsersEndpointUrl = usersEndpointUrl;
        mHttpRequester = httpRequester;
        mUsersJsonParser = jsonParser;
    }
    
    @Override
    public List<T> getAll() throws IOException
    {
        final String url = mUsersEndpointUrl + "/secured/getAll";
        String json = mHttpRequester.get(url);
        
        return mUsersJsonParser.fromJsonArray(json);
    }
    
    @Override
    public T getById(int id) throws IOException
    {
        final String url = mUsersEndpointUrl + "/get/" + id;
        String json = mHttpRequester.get(url);
        
        return mUsersJsonParser.fromJson(json);
    }
    
    @Override
    public T add(T item) throws Exception
    {
        final String url = mUsersEndpointUrl + "/signup";
        String requestBody = mUsersJsonParser.toJson(item);
        String responseBody = mHttpRequester.post(url, requestBody);
        
        return mUsersJsonParser.fromJson(responseBody);
    }
    
    @Override
    public T login(String username, String password) throws IOException
    {
        String responseBody = mHttpRequester.login(username, password);
        
        return mUsersJsonParser.fromJson(responseBody);
    }
}
