package com.rachev.getmydrivercardapp.http;

import com.rachev.getmydrivercardapp.http.base.HttpRequester;
import com.rachev.getmydrivercardapp.utils.Constants;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;

public class OkHttpHttpRequester implements HttpRequester
{
    public OkHttpHttpRequester()
    {
    }
    
    @Override
    public String get(String url) throws IOException
    {
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        
        OkHttpClient client = new OkHttpClient();
        
        return client.newCall(request)
                .execute()
                .body()
                .string();
    }
    
    @Override
    public String post(String url, String bodyString) throws IOException
    {
        RequestBody body = RequestBody.create(
                MediaType.parse(Constants.JSON_MEDIA_TYPE),
                bodyString);
        
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();
        
        OkHttpClient client = new OkHttpClient();
        
        return client.newCall(request)
                .execute()
                .body()
                .string();
    }
}
