package com.rachev.getmydrivercardapp.http.base;

import java.io.IOException;

public interface HttpRequester
{
    String get(String url) throws IOException;
    
    String post(String url, String bodyString) throws IOException;
    
    void put(String url, String bodyString) throws IOException;
    
    String login(String username, String password) throws IOException;
}
