package com.rachev.getmydrivercardbackend.notifications;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Service
public class AndroidPushNotificationsService
{
    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
    private static final String FIREBASE_SERVER_KEY = "AAAA-r1N2Nw:APA91bHFQ2NFjGlpmbR0rr" +
            "XcEmyqF15SmbGTSUtu6o6GAJyALz6gWbeGEgxe7TM8Pmmbq2f9I_3F428v9H79TMTKSPkWhe822" +
            "ZtiUK5RM8OswpQjX8_DYkRbadK9LXZcue_S8cqgNBD3";
    
    public CompletableFuture<String> send(HttpEntity<String> entity)
    {
        RestTemplate restTemplate = new RestTemplate();
        
        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequesterIncerceptor(
                "Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequesterIncerceptor(
                "Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);
        
        String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);
        
        return CompletableFuture.completedFuture(firebaseResponse);
    }
}

