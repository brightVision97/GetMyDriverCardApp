package com.rachev.getmydrivercardapp.models;

import java.util.Date;

public class ApplicantDetails
{
    private int id;
    private Date birthDate;
    private String egn;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private Address address;
    
    public ApplicantDetails()
    {
    }
    
    public ApplicantDetails(Date birthDate, String egn,
                            String firstName, String middleName,
                            String lastName, String email, Address address)
    {
        setBirthDate(birthDate);
        setEgn(egn);
        setFirstName(firstName);
        setMiddleName(middleName);
        setLastName(lastName);
        setEmail(email);
        setAddress(address);
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public Date getBirthDate()
    {
        return birthDate;
    }
    
    public void setBirthDate(Date birthDate)
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
    
    public Address getAddress()
    {
        return address;
    }
    
    public void setAddress(Address address)
    {
        this.address = address;
    }
}
