package com.rachev.getmydrivercardapp.repositories.base;

import com.rachev.getmydrivercardapp.models.BaseRequest;

import java.io.IOException;
import java.util.List;

public interface RequestsRepository extends BaseRepository<BaseRequest>
{
    List<BaseRequest> getAllRequestsByUserId(int userId) throws Exception;
    
    void updateStatus(BaseRequest baseRequest) throws IOException;
}
