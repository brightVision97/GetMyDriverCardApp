package com.rachev.getmydrivercardapp.http;

import com.rachev.getmydrivercardapp.http.base.HttpRequester;
import com.rachev.getmydrivercardapp.utils.Constants;
import okhttp3.*;

import java.io.IOException;

public class OkHttpHttpRequester implements HttpRequester
{
    private OkHttpHttpRequester()
    {
    }
    
    public static OkHttpHttpRequester getInstance()
    {
        return new OkHttpHttpRequester();
    }
    
    @Override
    public String get(String url) throws IOException
    {
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        
        return new OkHttpClient()
                .newCall(request)
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
        
        Response response = new OkHttpClient()
                .newCall(request)
                .execute();
        
        return response.code() == Constants.HTTP_STATUS_OK
                ? response.body().string()
                : null;
    }
}
