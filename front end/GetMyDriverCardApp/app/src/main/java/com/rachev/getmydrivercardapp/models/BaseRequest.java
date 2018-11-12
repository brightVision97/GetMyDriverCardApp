package com.rachev.getmydrivercardapp.models;

import java.io.Serializable;

public class BaseRequest implements Serializable
{
    private int id;
    private String type;
    private String status;
    private String renewalReason;
    private String replacementReason;
    private User user;
    private ApplicantDetails applicantDetails;
    private ImageAttachment imageAttachment;
    private String recordCreationDate;
    private String lastEditDate;
    private String tachCardIssuingCountry;
    private String tachCardNumber;
    private String drivingLicIssuingCountry;
    private String drivingLicNumber;
    private String replacementIncidentDate;
    private String replacementIncidentPlace;
    
    public BaseRequest()
    {
    }
    
    public BaseRequest(User user, String type, String status, ApplicantDetails applicantDetails)
    {
        setUser(user);
        setType(type);
        setStatus(status);
        setApplicantDetails(applicantDetails);
        setRenewalReason(null);
        setReplacementReason(null);
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getRenewalReason()
    {
        return renewalReason;
    }
    
    public void setRenewalReason(String renewalReason)
    {
        this.renewalReason = renewalReason;
    }
    
    public String getReplacementReason()
    {
        return replacementReason;
    }
    
    public void setReplacementReason(String replacementReason)
    {
        this.replacementReason = replacementReason;
    }
    
    public ApplicantDetails getApplicantDetails()
    {
        return applicantDetails;
    }
    
    public void setApplicantDetails(ApplicantDetails applicantDetails)
    {
        this.applicantDetails = applicantDetails;
    }
    
    public String getRecordCreationDate()
    {
        return recordCreationDate;
    }
    
    public String getLastEditDate()
    {
        return lastEditDate;
    }
    
    public User getUser()
    {
        return user;
    }
    
    public void setUser(User user)
    {
        this.user = user;
    }
    
    public ImageAttachment getImageAttachment()
    {
        return imageAttachment;
    }
    
    public void setImageAttachment(ImageAttachment imageAttachment)
    {
        this.imageAttachment = imageAttachment;
    }
    
    public String getTachCardIssuingCountry()
    {
        return tachCardIssuingCountry;
    }
    
    public void setTachCardIssuingCountry(String tachCardIssuingCountry)
    {
        this.tachCardIssuingCountry = tachCardIssuingCountry;
    }
    
    public String getTachCardNumber()
    {
        return tachCardNumber;
    }
    
    public void setTachCardNumber(String tachCardNumber)
    {
        this.tachCardNumber = tachCardNumber;
    }
    
    public String getDrivingLicIssuingCountry()
    {
        return drivingLicIssuingCountry;
    }
    
    public void setDrivingLicIssuingCountry(String drivingLicIssuingCountry)
    {
        this.drivingLicIssuingCountry = drivingLicIssuingCountry;
    }
    
    public String getDrivingLicNumber()
    {
        return drivingLicNumber;
    }
    
    public void setDrivingLicNumber(String drivingLicNumber)
    {
        this.drivingLicNumber = drivingLicNumber;
    }
    
    public String getReplacementIncidentDate()
    {
        return replacementIncidentDate;
    }
    
    public void setReplacementIncidentDate(String replacementIncidentDate)
    {
        this.replacementIncidentDate = replacementIncidentDate;
    }
    
    public String getReplacementIncidentPlace()
    {
        return replacementIncidentPlace;
    }
    
    public void setReplacementIncidentPlace(String replacementIncidentPlace)
    {
        this.replacementIncidentPlace = replacementIncidentPlace;
    }
}
