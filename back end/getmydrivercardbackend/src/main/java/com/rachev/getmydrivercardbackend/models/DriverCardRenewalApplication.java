package com.rachev.getmydrivercardbackend.models;

import com.rachev.getmydrivercardbackend.models.base.DriverCardApplication;
import com.rachev.getmydrivercardbackend.models.dtos.AddressDTO;
import com.rachev.getmydrivercardbackend.models.dtos.ApplicantDetailsDTO;
import com.rachev.getmydrivercardbackend.models.enums.RenewalReason;

// WIP
public class DriverCardRenewalApplication extends DriverCardApplication
{
    private RenewalReason renewalReason;
    
    {
        switch (renewalReason)
        {
            case EXPIRED:
                break;
            case SUSPENDED_OR_WITHDRAWN:
                break;
            default:
                break;
        }
    }
    
    public DriverCardRenewalApplication(ApplicantDetailsDTO applicantDetails)
    {
        super(applicantDetails);
    }
    
    @Override
    protected void payService(double fee, AddressDTO address)
    {
    
    }
}
