package com.rachev.getmydrivercardbackend.services.base;

import com.rachev.getmydrivercardbackend.models.base.BaseRequest;

import java.util.List;

public interface RequestsService
{
    List<BaseRequest> getAllRequests();
    
    List<BaseRequest> getRequestsById(int id);
    
    BaseRequest createRequest(BaseRequest baseRequest);
}
