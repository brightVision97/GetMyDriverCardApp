package com.rachev.getmydrivercardbackend.models;

public enum Reasons
{
    ;
    
    public enum Replacement
    {
        EXCHANGE_FOR_GB_CARD, LOST, STOLEN, MALFUNCTIONED, DAMAGED;
        
        private static final String TYPE_EXCHANGE_FOR_GB_CARD = "exchange";
        private static final String TYPE_LOST = "lost";
        private static final String TYPE_STOLEN = "stolen";
        private static final String TYPE_MALFUNCTIONED = "malfunctioned";
        private static final String TYPE_DAMAGED = "damaged";
        
        private static Reasons.Replacement fromName(String name)
        {
            switch (name)
            {
                case TYPE_EXCHANGE_FOR_GB_CARD:
                    return EXCHANGE_FOR_GB_CARD;
                case TYPE_LOST:
                    return LOST;
                case TYPE_STOLEN:
                    return STOLEN;
                case TYPE_MALFUNCTIONED:
                    return MALFUNCTIONED;
                case TYPE_DAMAGED:
                    return DAMAGED;
                default:
                    return null;
            }
        }
    }
    
    public enum Renewal
    {
        EXPIRED, SUSPENDED_OR_WITHDRAWN;
        
        private static final String TYPE_EXPIRED = "expired";
        private static final String TYPE_SUSPENDED_OR_WITHDRAWN = "suspended/withdrawn";
        
        private static Reasons.Renewal fromName(String name)
        {
            switch (name)
            {
                case TYPE_EXPIRED:
                    return EXPIRED;
                case TYPE_SUSPENDED_OR_WITHDRAWN:
                    return SUSPENDED_OR_WITHDRAWN;
                default:
                    return null;
            }
        }
    }
}
