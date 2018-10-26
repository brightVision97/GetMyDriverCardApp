package com.rachev.getmydrivercardbackend.models;

import com.rachev.getmydrivercardbackend.models.base.DriverCardApplication;
import com.rachev.getmydrivercardbackend.models.dtos.AddressDTO;
import com.rachev.getmydrivercardbackend.models.dtos.ApplicantDetailsDTO;
import com.rachev.getmydrivercardbackend.models.enums.ReplacementReason;

// WIP
public class DriverCardReplacementApplication extends DriverCardApplication
{
    private ReplacementReason replacementReason;
    
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
    
    public DriverCardReplacementApplication(ApplicantDetailsDTO applicantDetails)
    {
        super(applicantDetails);
    }
    
    @Override
    protected void payService(double price, AddressDTO address)
    {
    
    }
}
