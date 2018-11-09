package com.rachev.getmydrivercardbackend.services;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class AndroidPushNotificationsService
{
    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
    private static final String FIREBASE_SERVER_KEY = "AAAA-r1N2Nw:APA91bHFQ2NFjGlpmbR0rr" +
            "XcEmyqF15SmbGTSUtu6o6GAJyALz6gWbeGEgxe7TM8Pmmbq2f9I_3F428v9H79TMTKSPkWhe822" +
            "ZtiUK5RM8OswpQjX8_DYkRbadK9LXZcue_S8cqgNBD3";
    
    public static String sendPushNotification(String deviceToken, String message) throws IOException
    {
        String result = "";
        URL url = new URL(FIREBASE_API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key= " + FIREBASE_SERVER_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        
        JSONObject json = new JSONObject();
        
        try
        {
            json.put("to", deviceToken.trim());
            
            JSONObject data = new JSONObject();
            data.put("Key-1", message);
            json.put("data", data);
            
            JSONObject info = new JSONObject();
            info.put("title", "Driver card application status update");
            info.put("body", "Your card request is now approved");
            info.put("message", "");
            json.put("notification", info);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        
        try
        {
            OutputStreamWriter streamWriter = new OutputStreamWriter(conn.getOutputStream());
            streamWriter.write(json.toString());
            streamWriter.flush();
            
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            
            String output;
            System.out.println("Output from Server ... ");
            while ((output = br.readLine()) != null)
                System.out.println(output);
            
            result = "succcess";
        } catch (Exception e)
        {
            e.printStackTrace();
            result = "failure";
        }
        System.out.println("GCM Notification was sent successfully");
        
        return result;
    }
}

