package com.rachev.getmydrivercardbackend.models;

import com.rachev.getmydrivercardbackend.models.base.Request;

// WIP
public class DriverCardRenewalRequest extends Request
{
    private Reasons.Renewal renewalReason;
    
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
    
    public DriverCardRenewalRequest(ApplicantDetails applicantDetails)
    {
        super(applicantDetails);
    }
    
    @Override
    protected void pay(double fee, Address address)
    {
    
    }
}
