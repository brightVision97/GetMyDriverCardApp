package com.rachev.getmydrivercardapp.repositories;

import com.rachev.getmydrivercardapp.http.base.HttpRequester;
import com.rachev.getmydrivercardapp.parsers.base.JsonParser;
import com.rachev.getmydrivercardapp.repositories.base.Repository;

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
    public T getById(int id) throws IOException
    {
        String url = mServerUrl + "/" + id;
        String json = mHttpRequester.get(url);
        
        return mJsonParser.fromJson(json);
    }
    
    @Override
    public T add(T item) throws IOException
    {
        String requestBody = mJsonParser.toJson(item);
        String responseBody = mHttpRequester.post(mServerUrl, requestBody);
        
        return mJsonParser.fromJson(responseBody);
    }
}
