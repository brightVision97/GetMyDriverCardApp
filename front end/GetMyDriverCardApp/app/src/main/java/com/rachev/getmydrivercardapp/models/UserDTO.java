package com.rachev.getmydrivercardapp.models;

import com.rachev.getmydrivercardapp.utils.Constants;

import java.io.Serializable;

public class UserDTO implements Serializable
{
    public int id;
    public String loginType;
    public String email;
    public String password;
    public String socialId;
    public String role;
    
    public UserDTO()
    {
    }
    
    public UserDTO(String socialId)
    {
        setSocialId(socialId);
        setRole(Constants.USER_ROLE);
        setEmail(null);
        setPassword(null);
    }
    
    public UserDTO(String email, String password)
    {
        setEmail(email);
        setPassword(password);
        setLoginType(Constants.LOGIN_TYPE_CUSTOM);
        setRole(Constants.USER_ROLE);
        setSocialId(null);
    }
    
    @Override
    public boolean equals(Object obj)
    {
        return socialId != null
                ? getSocialId().equals(((UserDTO) obj).getSocialId())
                : getEmail().equals(((UserDTO) obj).getEmail());
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getLoginType()
    {
        return loginType;
    }
    
    public void setLoginType(String loginType)
    {
        this.loginType = loginType;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public String getSocialId()
    {
        return socialId;
    }
    
    public void setSocialId(String socialId)
    {
        this.socialId = socialId;
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
