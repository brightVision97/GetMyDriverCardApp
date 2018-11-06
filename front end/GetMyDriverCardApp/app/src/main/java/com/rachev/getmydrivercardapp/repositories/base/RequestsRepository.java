package com.rachev.getmydrivercardapp.repositories.base;

import com.rachev.getmydrivercardapp.models.base.BaseRequest;

import java.util.List;

public interface RequestsRepository
{
    List<BaseRequest> getAll() throws Exception;
    
    List<BaseRequest> getAllRequestsById(int id) throws Exception;
}
