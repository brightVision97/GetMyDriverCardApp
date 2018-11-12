package com.rachev.getmydrivercardapp.utils.enums;

public enum RequestStatus
{
    PENDING, APPROVED, DISAPPROVED,
    ;
    
    private static final String TYPE_PENDING = "pending";
    private static final String TYPE_APPROVED = "approved";
    private static final String TYPE_DISAPPROVED = "disapproved";
    
    @Override
    public String toString()
    {
        switch (this)
        {
            case PENDING:
                return TYPE_PENDING;
            case APPROVED:
                return TYPE_APPROVED;
            case DISAPPROVED:
                return TYPE_DISAPPROVED;
            default:
                return null;
        }
    }
}
