package com.rachev.getmydrivercardapp.models.base;

import com.rachev.getmydrivercardapp.models.ApplicantDetails;
import com.rachev.getmydrivercardapp.models.Image;

import java.util.List;

public abstract class BaseRequest
{
    private int id;
    private int userId;
    private String type;
    private String status;
    private ApplicantDetails applicantDetails;
    private List<Image> attachments;
    
    protected BaseRequest()
    {
    }
    
    protected BaseRequest(int userId, String type,
                          ApplicantDetails applicantDetails,
                          List<Image> attachments)
    {
        setUserId(userId);
        setType(type);
        setApplicantDetails(applicantDetails);
        setAttachments(attachments);
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getUserId()
    {
        return userId;
    }
    
    public void setUserId(int userId)
    {
        this.userId = userId;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public ApplicantDetails getApplicantDetails()
    {
        return applicantDetails;
    }
    
    public void setApplicantDetails(ApplicantDetails applicantDetails)
    {
        this.applicantDetails = applicantDetails;
    }
    
    public List<Image> getAttachments()
    {
        return attachments;
    }
    
    public void setAttachments(List<Image> attachments)
    {
        this.attachments = attachments;
    }
}
