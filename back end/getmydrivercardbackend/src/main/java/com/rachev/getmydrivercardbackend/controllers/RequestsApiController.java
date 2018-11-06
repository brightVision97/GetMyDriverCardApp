package com.rachev.getmydrivercardbackend.controllers;

import com.rachev.getmydrivercardbackend.models.base.BaseRequest;
import com.rachev.getmydrivercardbackend.services.base.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestsApiController
{
    private final RequestsService requestsService;
    
    @Autowired
    public RequestsApiController(RequestsService requestsService)
    {
        this.requestsService = requestsService;
    }
    
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/secured/getAll")
    public List<BaseRequest> getAllRequests()
    {
        return requestsService.getAllRequests();
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/secured/getAll/{id}")
    public List<BaseRequest> getAllRequestsById(@PathVariable int id)
    {
        return requestsService.getRequestsById(id);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @ResponseBody
    @PostMapping("/secured/create")
    public BaseRequest createRequest(@RequestBody BaseRequest baseRequest)
    {
        return requestsService.createRequest(baseRequest);
    }
}
