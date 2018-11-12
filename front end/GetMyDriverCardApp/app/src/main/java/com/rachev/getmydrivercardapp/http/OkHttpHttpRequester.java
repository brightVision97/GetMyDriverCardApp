package com.rachev.getmydrivercardapp.http;

import android.content.Context;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.rachev.getmydrivercardapp.GetMyDriverCardApplication;
import com.rachev.getmydrivercardapp.http.base.HttpRequester;
import com.rachev.getmydrivercardapp.utils.Constants;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;

public class OkHttpHttpRequester implements HttpRequester
{
    private ClearableCookieJar mCookieJar;
    private HttpLoggingInterceptor mInterceptor;
    
    public OkHttpHttpRequester(Context context)
    {
        mCookieJar = GetMyDriverCardApplication.getCookieJar(context);
        mInterceptor = new HttpLoggingInterceptor();
        mInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }
    
    public static OkHttpHttpRequester getInstance(Context context)
    {
        return new OkHttpHttpRequester(context);
    }
    
    @Override
    public String get(String url) throws IOException
    {
        return new OkHttpClient.Builder()
                .cookieJar(mCookieJar)
                .addInterceptor(mInterceptor)
                .build()
                .newCall(new Request.Builder()
                        .get()
                        .url(url)
                        .build())
                .execute()
                .body()
                .string();
    }
    
    @Override
    public String post(String url, String bodyString) throws IOException
    {
        return new OkHttpClient.Builder()
                .cookieJar(mCookieJar)
                .addInterceptor(mInterceptor)
                .build()
                .newCall(new Request.Builder()
                        .post(RequestBody.create(
                                MediaType.parse("application/json"),
                                bodyString))
                        .url(url)
                        .build())
                .execute()
                .body()
                .string();
    }
    
    @Override
    public void put(String url, String bodyString) throws IOException
    {
        new OkHttpClient.Builder()
                .cookieJar(mCookieJar)
                .addInterceptor(mInterceptor)
                .build()
                .newCall(new Request.Builder()
                        .put(RequestBody.create(
                                MediaType.parse("application/json"),
                                bodyString))
                        .url(url)
                        .build())
                .execute();
    }
    
    @Override
    public String login(String username, String password) throws IOException
    {
        String url = Constants.Strings.BASE_SERVER_URL +
                Constants.Strings.USERS_URL_SUFFIX +
                Constants.Strings.USER_ME_SUFFIX;
        
        return new OkHttpClient.Builder()
                .cookieJar(mCookieJar)
                .addInterceptor(mInterceptor)
                .authenticator((route, response) ->
                {
                    if (responseCount(response) >= 3)
                        return null;
                    
                    String credentials = Credentials.basic(username, password);
                    
                    return response.request()
                            .newBuilder()
                            .header("Authorization", credentials)
                            .build();
                })
                .build()
                .newCall(new Request.Builder()
                        .addHeader("username", username)
                        .addHeader("password", password)
                        .get()
                        .url(url)
                        .build())
                .execute()
                .body()
                .string();
    }
    
    private int responseCount(Response response)
    {
        int result = 1;
        while ((response = response.priorResponse()) != null)
            ++result;
        
        return result;
    }
}
