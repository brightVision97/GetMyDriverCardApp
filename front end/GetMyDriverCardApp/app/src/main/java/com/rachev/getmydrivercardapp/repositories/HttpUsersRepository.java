package com.rachev.getmydrivercardapp.repositories;

import com.rachev.getmydrivercardapp.http.base.HttpRequester;
import com.rachev.getmydrivercardapp.parsers.base.JsonParser;
import com.rachev.getmydrivercardapp.repositories.base.Repository;

import java.io.IOException;
import java.util.List;

public class HttpUsersRepository<T> implements Repository<T>
{
    private final HttpRequester mHttpRequester;
    private final String mUsersEndpointUrl;
    private final JsonParser<T> mUsersJsonParser;
    
    public HttpUsersRepository(String usersEndpointUrl,
                               HttpRequester httpRequester,
                               JsonParser<T> jsonParser)
    {
        mUsersEndpointUrl = usersEndpointUrl;
        mHttpRequester = httpRequester;
        mUsersJsonParser = jsonParser;
    }
    
    @Override
    public List<T> getAll() throws IOException
    {
        String json = mHttpRequester.get(mUsersEndpointUrl);
        
        return mUsersJsonParser.fromJsonArray(json);
    }
    
    @Override
    public T add(T item) throws Exception
    {
        String url = mUsersEndpointUrl + "/signup";
        String requestBody = mUsersJsonParser.toJson(item);
        String responseBody = mHttpRequester.post(url, requestBody);
        
        return mUsersJsonParser.fromJson(responseBody);
    }
}
