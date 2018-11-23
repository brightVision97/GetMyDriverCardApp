package com.rachev.getmydrivercardapp.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable
{
    private int id;
    private String username;
    private String password;
    private String facebookId;
    private String googleId;
    private Set<Role> roles;
    
    public User()
    {
    }
    
    public User(String username, String password)
    {
        setUsername(username);
        setPassword(password);
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
    
    public Set<Role> getRoles()
    {
        return new HashSet<>(roles);
    }
    
    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
    }
}
