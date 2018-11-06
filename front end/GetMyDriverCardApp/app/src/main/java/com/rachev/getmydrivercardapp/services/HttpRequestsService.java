package com.rachev.getmydrivercardapp.services;

import com.rachev.getmydrivercardapp.models.base.BaseRequest;
import com.rachev.getmydrivercardapp.repositories.base.RequestsRepository;
import com.rachev.getmydrivercardapp.services.base.RequestsService;

import java.util.List;

public class HttpRequestsService implements RequestsService
{
    private final RequestsRepository mRequestsRepository;
    
    public HttpRequestsService(RequestsRepository requestsRepository)
    {
        mRequestsRepository = requestsRepository;
    }
    
    @Override
    public List<BaseRequest> getAllRequests() throws Exception
    {
        return mRequestsRepository.getAll();
    }
    
    @Override
    public List<BaseRequest> getAllRequestsById(int id) throws Exception
    {
        return mRequestsRepository.getAllRequestsById(id);
    }
}
