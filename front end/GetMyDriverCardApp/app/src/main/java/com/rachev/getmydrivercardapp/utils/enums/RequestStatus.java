package com.rachev.getmydrivercardapp.utils.enums;

public enum RequestStatus
{
    PENDING, APPROVED,
    ;
    
    private static final String TYPE_PENDING = "pending";
    private static final String TYPE_APPROVED = "approved";
    
    @Override
    public String toString()
    {
        switch (this)
        {
            case PENDING:
                return TYPE_PENDING;
            case APPROVED:
                return TYPE_APPROVED;
            default:
                return null;
        }
    }
}
