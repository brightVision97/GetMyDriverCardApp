package com.rachev.getmydrivercardapp.utils.enums;

public enum RequestType
{
    NEW, RENEW, REPLACE,
    ;
    
    private static final String TYPE_NEW = "new";
    private static final String TYPE_RENEW = "renew";
    private static final String TYPE_REPLACE = "replace";
    
    @Override
    public String toString()
    {
        switch (this)
        {
            case NEW:
                return TYPE_NEW;
            case RENEW:
                return TYPE_RENEW;
            case REPLACE:
                return TYPE_REPLACE;
            default:
                return null;
        }
    }
}
