package com.rachev.getmydrivercardbackend.services.base;

import com.rachev.getmydrivercardbackend.models.BaseRequest;

import java.util.List;

public interface RequestsService extends BaseService<BaseRequest>
{
    List<BaseRequest> getAllByUserId(int userId);
    
    void updateRequestStatus(int requestId, String requestStatus);
}
