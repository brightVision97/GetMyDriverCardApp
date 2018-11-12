package com.rachev.getmydrivercardapp.services.base;

import com.rachev.getmydrivercardapp.models.BaseRequest;

import java.util.List;

public interface RequestsService extends BaseService<BaseRequest>
{
    List<BaseRequest> getAllRequestsByUserId(int userId) throws Exception;
    
    void updateStatus(BaseRequest baseRequest) throws Exception;
}
