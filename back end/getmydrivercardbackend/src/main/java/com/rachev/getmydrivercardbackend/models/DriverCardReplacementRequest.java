package com.rachev.getmydrivercardbackend.models;

import com.rachev.getmydrivercardbackend.models.base.Request;

// WIP
public class DriverCardReplacementRequest extends Request
{
    private Reasons.Replacement replacementReason;
    
    {
        switch (replacementReason)
        {
            case EXCHANGE_FOR_GB_CARD:
                break;
            case LOST:
                break;
            case STOLEN:
                break;
            case MALFUNCTIONED:
                break;
            case DAMAGED:
                break;
            default:
                break;
        }
    }
    
    public DriverCardReplacementRequest(ApplicantDetails applicantDetails)
    {
        super(applicantDetails);
    }
    
    @Override
    protected void send(Address address)
    {
    
    }
    
    @Override
    protected void pay(double fee)
    {
    
    }
}
