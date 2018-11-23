package com.rachev.getmydrivercardapp.repositories;

import com.rachev.getmydrivercardapp.http.base.HttpRequester;
import com.rachev.getmydrivercardapp.models.BaseRequest;
import com.rachev.getmydrivercardapp.parsers.base.JsonParser;
import com.rachev.getmydrivercardapp.repositories.base.RequestsRepository;
import com.rachev.getmydrivercardapp.utils.Constants;

import java.io.IOException;
import java.util.List;

public class HttpRequestsRepository implements RequestsRepository
{
    private final HttpRequester mHttpRequester;
    private final String mRequestsEndpointUrl;
    private final JsonParser<BaseRequest> mRequestsJsonParser;
    
    public HttpRequestsRepository(String requestsEndpointUrl, HttpRequester httpRequester,
                                  JsonParser<BaseRequest> requestsJsonParser)
    {
        mHttpRequester = httpRequester;
        mRequestsEndpointUrl = requestsEndpointUrl;
        mRequestsJsonParser = requestsJsonParser;
    }
    
    @Override
    public List<BaseRequest> getAll() throws Exception
    {
        final String url = mRequestsEndpointUrl + "/secured" +
                Constants.Strings.ALL_REQUESTS_SUFFIX;
        String json = mHttpRequester.get(url);
        
        return mRequestsJsonParser.fromJsonArray(json);
    }
    
    @Override
    public BaseRequest getById(int id) throws IOException
    {
        final String url = mRequestsEndpointUrl + "/get/" + id;
        String json = mHttpRequester.get(url);
        
        return mRequestsJsonParser.fromJson(json);
    }
    
    @Override
    public BaseRequest add(BaseRequest item) throws Exception
    {
        final String url = mRequestsEndpointUrl + "/create";
        String requestBody = mRequestsJsonParser.toJson(item);
        String responseBody = mHttpRequester.post(url, requestBody);
        
        return mRequestsJsonParser.fromJson(responseBody);
    }
    
    @Override
    public List<BaseRequest> getAllRequestsByUserId(int userId) throws Exception
    {
        final String url = mRequestsEndpointUrl + "/secured/get/" + userId;
        String requestsJson = mHttpRequester.get(url);
        
        return mRequestsJsonParser.fromJsonArray(requestsJson);
    }
    
    @Override
    public void updateStatus(BaseRequest baseRequest) throws IOException
    {
        final String url = mRequestsEndpointUrl + "/secured/update";
        String requestBody = mRequestsJsonParser.toJson(baseRequest);
        
        mHttpRequester.put(url, requestBody);
    }
}
