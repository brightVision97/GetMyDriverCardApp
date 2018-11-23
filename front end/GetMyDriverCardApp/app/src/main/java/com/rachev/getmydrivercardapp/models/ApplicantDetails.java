package com.rachev.getmydrivercardapp.models;

import java.io.Serializable;

public class ApplicantDetails implements Serializable
{
    private int id;
    private String egn;
    private String birthDate;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
    
    public ApplicantDetails()
    {
    }
    
    public ApplicantDetails(String egn, String birthDate, String firstName, String middleName,
                            String lastName, String address, String email, String phoneNumber)
    {
        setEgn(egn);
        setBirthDate(birthDate);
        setFirstName(firstName);
        setMiddleName(middleName);
        setLastName(lastName);
        setAddress(address);
        setEmail(email);
        setPhoneNumber(phoneNumber);
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getBirthDate()
    {
        return birthDate;
    }
    
    public void setBirthDate(String birthDate)
    {
        this.birthDate = birthDate;
    }
    
    public String getEgn()
    {
        return egn;
    }
    
    public void setEgn(String egn)
    {
        this.egn = egn;
    }
    
    public String getFirstName()
    {
        return firstName;
    }
    
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    
    public String getMiddleName()
    {
        return middleName;
    }
    
    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }
    
    public String getLastName()
    {
        return lastName;
    }
    
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
}
