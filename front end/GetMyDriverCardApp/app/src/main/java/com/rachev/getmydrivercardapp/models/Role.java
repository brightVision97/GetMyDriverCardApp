package com.rachev.getmydrivercardapp.models;

import java.io.Serializable;

public class Role implements Serializable
{
    private int id;
    private String roleName;
    
    public Role()
    {
    }
    
    public Role(int id, String roleName)
    {
        setId(id);
        setRoleName(roleName);
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getRoleName()
    {
        return roleName;
    }
    
    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }
}
