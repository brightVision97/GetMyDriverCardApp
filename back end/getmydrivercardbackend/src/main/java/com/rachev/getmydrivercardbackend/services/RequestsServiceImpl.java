package com.rachev.getmydrivercardbackend.services;

import com.rachev.getmydrivercardbackend.models.BaseRequest;
import com.rachev.getmydrivercardbackend.repositories.RequestsRepository;
import com.rachev.getmydrivercardbackend.services.base.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<BaseRequest> getAll()
    {
        return requestsRepository.findAll();
    }
    
    @Override
    public BaseRequest getById(int id)
    {
        return requestsRepository.findById(id)
                .orElse(null);
    }
    
    @Override
    public BaseRequest add(BaseRequest object)
    {
        return requestsRepository.save(object);
    }
    
    @Override
    public List<BaseRequest> getAllByUserId(int userId)
    {
        return requestsRepository.getAllByUserId(userId);
    }
    
    @Override
    public void updateRequestStatus(int requestId, String requestStatus)
    {
        requestsRepository.setRequestStatusById(requestId, requestStatus);
    }
}
