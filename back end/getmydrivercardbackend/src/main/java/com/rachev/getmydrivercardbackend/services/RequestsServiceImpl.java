package com.rachev.getmydrivercardbackend.services;

import com.rachev.getmydrivercardbackend.models.base.BaseRequest;
import com.rachev.getmydrivercardbackend.repositories.RequestsRepository;
import com.rachev.getmydrivercardbackend.services.base.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RequestsServiceImpl implements RequestsService
{
    private final RequestsRepository requestsRepository;
    
    @Autowired
    public RequestsServiceImpl(RequestsRepository requestsRepository)
    {
        this.requestsRepository = requestsRepository;
    }
    
    @Override
    public List<BaseRequest> getAllRequests()
    {
        return requestsRepository.findAll();
    }
    
    @Override
    public List<BaseRequest> getRequestsById(int id)
    {
        return requestsRepository.findAllById(Collections.singletonList(id));
    }
    
    @Override
    public BaseRequest createRequest(BaseRequest baseRequest)
    {
        return requestsRepository.save(baseRequest);
    }
}
