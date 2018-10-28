package com.rachev.getmydrivercardapp.models;

import com.rachev.getmydrivercardapp.utils.Constants;

import java.io.Serializable;

public class User implements Serializable
{
    public int id;
    public String username;
    public String password;
    public String facebookId;
    public String googleId;
    public String role;
    
    public User()
    {
    }
    
    public User(String username, String password)
    {
        setUsername(username);
        setPassword(password);
        setRole(Constants.Strings.USER_ROLE);
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public String getFacebookId()
    {
        return facebookId;
    }
    
    public void setFacebookId(String facebookId)
    {
        this.facebookId = facebookId;
    }
    
    public String getGoogleId()
    {
        return googleId;
    }
    
    public void setGoogleId(String googleId)
    {
        this.googleId = googleId;
    }
    
    public String getRole()
    {
        return role;
    }
    
    public void setRole(String role)
    {
        this.role = role;
    }
}
