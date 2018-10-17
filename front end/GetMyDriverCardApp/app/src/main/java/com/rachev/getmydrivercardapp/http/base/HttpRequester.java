package com.rachev.getmydrivercardapp.http.base;

import java.io.IOException;

public interface HttpRequester
{
    String get(String url) throws IOException;
    
    String post(String url, String bodyString) throws IOException;
}
