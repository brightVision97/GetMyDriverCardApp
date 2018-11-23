package com.rachev.getmydrivercardbackend.controllers;

import com.rachev.getmydrivercardbackend.models.BaseRequest;
import com.rachev.getmydrivercardbackend.notifications.AndroidPushNotificationsService;
import com.rachev.getmydrivercardbackend.services.base.RequestsService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/requests")
public class RequestsApiController
{
    private final AndroidPushNotificationsService androidPushNotificationsService;
    private final RequestsService requestsService;
    
    @Autowired
    public RequestsApiController(AndroidPushNotificationsService androidPushNotificationsService,
                                 RequestsService requestsService)
    {
        this.androidPushNotificationsService = androidPushNotificationsService;
        this.requestsService = requestsService;
    }
    
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/secured/getAll")
    public List<BaseRequest> getAllRequests()
    {
        return requestsService.getAll();
    }
    
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/secured/get/{id}")
    public List<BaseRequest> getAllRequestsById(@PathVariable int id)
    {
        return requestsService.getAllByUserId(id);
    }
    
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/get/{id}")
    public BaseRequest getRequestById(@PathVariable int id)
    {
        return requestsService.getById(id);
    }
    
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/create")
    public BaseRequest createRequest(@RequestBody BaseRequest baseRequest)
    {
        return requestsService.add(baseRequest);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/secured/update/")
    public ResponseEntity<String> updateRequestStatus(@RequestBody BaseRequest baseRequest)
    {
        requestsService.updateRequestStatus(baseRequest.getId(), baseRequest.getStatus());
        
        HttpEntity<String> request = null;
        try
        {
            JSONObject body = new JSONObject();
            body.put("to", "/topics/" + baseRequest.getUser().getUsername());
            body.put("priority", "high");
            
            JSONObject notification = new JSONObject();
            notification.put("title", "Driver card application update");
            notification.put("body", "The status of your request with number " +
                    baseRequest.getId() + " was just changed to " + baseRequest.getStatus());
            
            JSONObject data = new JSONObject();
            data.put("username", baseRequest.getUser().getUsername());
            data.put("request_id", baseRequest.getId());
            data.put("request_status", baseRequest.getStatus());
            
            body.put("notification", notification);
            body.put("data", data);
            
            request = new HttpEntity<>(body.toString());
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        
        CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
        CompletableFuture.allOf(pushNotification).join();
        
        try
        {
            String firebaseResponse = pushNotification.get();
            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        } catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }
        
        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }
}
