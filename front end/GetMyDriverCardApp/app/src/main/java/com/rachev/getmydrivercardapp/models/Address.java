package com.rachev.getmydrivercardapp.models;

public class Address
{
    private int id;
    private String city;
    private String street;
    private String postcode;
    
    public Address()
    {
    }
    
    public Address(String city, String street, String postcode)
    {
        setCity(city);
        setStreet(street);
        setPostcode(postcode);
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getCity()
    {
        return city;
    }
    
    public void setCity(String city)
    {
        this.city = city;
    }
    
    public String getStreet()
    {
        return street;
    }
    
    public void setStreet(String street)
    {
        this.street = street;
    }
    
    public String getPostcode()
    {
        return postcode;
    }
    
    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
    }
}
