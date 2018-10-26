package com.rachev.getmydrivercardbackend.models.base;

import com.rachev.getmydrivercardbackend.models.dtos.ApplicantDetailsDTO;
import com.rachev.getmydrivercardbackend.models.dtos.AddressDTO;

// WIP
public abstract class DriverCardApplication
{
    private ApplicantDetailsDTO applicantDetails;
    
    public DriverCardApplication(ApplicantDetailsDTO applicantDetails)
    {
        setApplicantDetails(applicantDetails);
    }
    
    public ApplicantDetailsDTO getApplicantDetails()
    {
        return applicantDetails;
    }
    
    public void setApplicantDetails(ApplicantDetailsDTO applicantDetails)
    {
        this.applicantDetails = applicantDetails;
    }
    
    protected abstract void payService(double fee, AddressDTO address);
}
