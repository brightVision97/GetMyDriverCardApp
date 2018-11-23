package com.rachev.getmydrivercardapp.services;

import android.content.Context;
import com.rachev.getmydrivercardapp.GetMyDriverCardApplication;
import com.rachev.getmydrivercardapp.models.BaseRequest;
import com.rachev.getmydrivercardapp.repositories.base.RequestsRepository;
import com.rachev.getmydrivercardapp.services.base.RequestsService;

import java.util.List;

public class HttpRequestsService implements RequestsService
{
    private final RequestsRepository mRequestsRepository;
    
    public HttpRequestsService(Context context)
    {
        mRequestsRepository = GetMyDriverCardApplication.getRequestsRepository(context);
    }
    
    @Override
    public List<BaseRequest> getAll() throws Exception
    {
        return mRequestsRepository.getAll();
    }
    
    @Override
    public BaseRequest create(BaseRequest request) throws Exception
    {
        return mRequestsRepository.add(request);
    }
    
    @Override
    public List<BaseRequest> getAllRequestsByUserId(int userId) throws Exception
    {
        return mRequestsRepository.getAllRequestsByUserId(userId);
    }
    
    @Override
    public void updateStatus(BaseRequest baseRequest) throws Exception
    {
        mRequestsRepository.updateStatus(baseRequest);
    }
}
