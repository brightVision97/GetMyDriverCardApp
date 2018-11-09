package com.rachev.getmydrivercardapp.repositories;

import com.rachev.getmydrivercardapp.http.base.HttpRequester;
import com.rachev.getmydrivercardapp.models.base.BaseRequest;
import com.rachev.getmydrivercardapp.parsers.base.JsonParser;
import com.rachev.getmydrivercardapp.repositories.base.RequestsRepository;
import com.rachev.getmydrivercardapp.utils.Constants;

import java.util.List;

public class HttpRequestsRepository implements RequestsRepository
{
    private final HttpRequester mHttpRequester;
    private final String mRequestsEndpointUrl;
    private final JsonParser<BaseRequest> mRequestsJsonParser;
    
    public HttpRequestsRepository(String requestsEndpointUrl,
                                  HttpRequester httpRequester,
                                  JsonParser<BaseRequest> requestsJsonParser)
    {
        mHttpRequester = httpRequester;
        mRequestsEndpointUrl = requestsEndpointUrl;
        mRequestsJsonParser = requestsJsonParser;
    }
    
    @Override
    public List<BaseRequest> getAll() throws Exception
    {
        String json = mHttpRequester.get(
                mRequestsEndpointUrl + Constants.Strings.ALL_REQUESTS_SUFFIX);
        
        return mRequestsJsonParser.fromJsonArray(json);
    }
    
    @Override
    public List<BaseRequest> getAllRequestsById(int id) throws Exception
    {
        String requestsJson = mHttpRequester.get(
                mRequestsEndpointUrl + Constants.Strings.ALL_REQUESTS_SUFFIX + "/" + id);
        
        return mRequestsJsonParser.fromJsonArray(requestsJson);
    }
}
