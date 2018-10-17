package com.rachev.getmydrivercardapp.repositories;

import com.rachev.getmydrivercardapp.GetMyDriverCardApplication;
import com.rachev.getmydrivercardapp.http.base.HttpRequester;
import com.rachev.getmydrivercardapp.models.UserDTO;
import com.rachev.getmydrivercardapp.parsers.GsonJsonParser;
import com.rachev.getmydrivercardapp.parsers.base.JsonParser;
import com.rachev.getmydrivercardapp.repositories.base.UsersRepository;
import com.rachev.getmydrivercardapp.utils.Constants;

import java.io.IOException;
import java.util.List;

public class HttpUsersRepository implements UsersRepository
{
    private final HttpRequester mHttpRequester;
    private final String mServerUrl;
    private final JsonParser<UserDTO> mJsonParser;
    
    public HttpUsersRepository()
    {
        mServerUrl = Constants.BASE_SERVER_URL + "/users";
        mHttpRequester = GetMyDriverCardApplication.getHttpRequester();
        mJsonParser = new GsonJsonParser<>(UserDTO.class, UserDTO[].class);
    }
    
    @Override
    public List<UserDTO> getAll() throws IOException
    {
        String usersJson = mHttpRequester.get(mServerUrl);
        
        return mJsonParser.fromJsonArray(usersJson);
    }
    
    @Override
    public UserDTO getById(int id) throws IOException
    {
        String url = mServerUrl + "/" + id;
        String json = mHttpRequester.get(url);
        
        return mJsonParser.fromJson(json);
    }
    
    @Override
    public UserDTO add(UserDTO item) throws IOException
    {
        String requestBody = mJsonParser.toJson(item);
        String responseBody = mHttpRequester.post(mServerUrl, requestBody);
        
        return mJsonParser.fromJson(responseBody);
    }
}
