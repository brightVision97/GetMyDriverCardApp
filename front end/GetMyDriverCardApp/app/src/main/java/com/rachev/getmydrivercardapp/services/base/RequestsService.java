package com.rachev.getmydrivercardapp.services.base;

import com.rachev.getmydrivercardapp.models.base.BaseRequest;

import java.util.List;

public interface RequestsService
{
    List<BaseRequest> getAllRequests() throws Exception;
    
    List<BaseRequest> getAllRequestsById(int id) throws Exception;
}
