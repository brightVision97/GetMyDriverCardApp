package com.rachev.getmydrivercardbackend.models.base;

import com.rachev.getmydrivercardbackend.models.ApplicantDetails;
import com.rachev.getmydrivercardbackend.models.Address;

// WIP
public abstract class Request
{
    private ApplicantDetails applicantDetails;
    
    public Request(ApplicantDetails applicantDetails)
    {
        setApplicantDetails(applicantDetails);
    }
    
    public ApplicantDetails getApplicantDetails()
    {
        return applicantDetails;
    }
    
    public void setApplicantDetails(ApplicantDetails applicantDetails)
    {
        this.applicantDetails = applicantDetails;
    }
    
    protected abstract void pay(double fee, Address address);
}
