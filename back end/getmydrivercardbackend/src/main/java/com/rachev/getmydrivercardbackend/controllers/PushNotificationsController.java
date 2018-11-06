package com.rachev.getmydrivercardbackend.controllers;

import com.rachev.getmydrivercardbackend.services.AndroidPushNotificationsService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/pushnotify")
public class PushNotificationsController
{
    private final AndroidPushNotificationsService androidPushNotificationsService;
    
    @Autowired
    public PushNotificationsController(AndroidPushNotificationsService androidPushNotificationsService)
    {
        this.androidPushNotificationsService = androidPushNotificationsService;
    }
    
    @PostMapping(value = "/send", produces = "application/json")
    public ResponseEntity<String> send() throws JSONException
    {
        JSONObject body = new JSONObject();
        body.put("to", "/topics/" + "Driver Cards");
        body.put("priority", "high");
        
        JSONObject notification = new JSONObject();
        notification.put("title", "Driver card status update");
        notification.put("body", "Your card request status was changed to approved");
        
        body.put("notification", notification);
        
        HttpEntity<String> request = new HttpEntity<>(body.toString());
        String deviceToken = "cfpNjsjN5dU:APA91bG1FQaSX_qgyeMX7ETlx23A9O1j8rEL0ccL7" +
                "cWDK2ig4ybd0AdlUwD5xm1b8l0IMDUjSvpf7NN0CfdQUhLS4cztEB_-iSvh3hXX2p5CO" +
                "IqrDnnTDC7QhbovqXB9gxrTK3yet9ob";
        try
        {
            String pushNotification = AndroidPushNotificationsService.sendPushNotification(
                    deviceToken, "Your driver card request is now approved");
            return new ResponseEntity<>(pushNotification, HttpStatus.OK);
        } catch (IOException ignored)
        {
        }
        
        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }
}
