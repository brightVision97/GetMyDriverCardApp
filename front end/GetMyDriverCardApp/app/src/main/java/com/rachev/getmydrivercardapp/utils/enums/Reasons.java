package com.rachev.getmydrivercardapp.utils.enums;

public enum Reasons
{
    ;
    
    public enum Replacement
    {
        EXCHANGE_FOR_BG_CARD, LOST, STOLEN, MALFUNCTIONED, DAMAGED,
        ;
        
        private static final String TYPE_EXCHANGE_FOR_BG_CARD = "exchange";
        private static final String TYPE_LOST = "lost";
        private static final String TYPE_STOLEN = "stolen";
        private static final String TYPE_MALFUNCTIONED = "malfunctioned";
        private static final String TYPE_DAMAGED = "damaged";
        
        @Override
        public String toString()
        {
            switch (this)
            {
                case EXCHANGE_FOR_BG_CARD:
                    return TYPE_EXCHANGE_FOR_BG_CARD;
                case LOST:
                    return TYPE_LOST;
                case STOLEN:
                    return TYPE_STOLEN;
                case MALFUNCTIONED:
                    return TYPE_MALFUNCTIONED;
                case DAMAGED:
                    return TYPE_DAMAGED;
                default:
                    return null;
            }
        }
    }
    
    public enum Renewal
    {
        EXPIRED, SUSPENDED_OR_WITHDRAWN, CHANGE_NAME, UPDATE_ADDRESS,
        ;
        
        private static final String TYPE_EXPIRED = "expired";
        private static final String TYPE_SUSPENDED_OR_WITHDRAWN = "suspended/withdrawn";
        private static final String TYPE_CHANGE_NAME = "change_name";
        private static final String TYPE_UPDATE_ADDRESS = "update_address";
        
        @Override
        public String toString()
        {
            switch (this)
            {
                case EXPIRED:
                    return TYPE_EXPIRED;
                case SUSPENDED_OR_WITHDRAWN:
                    return TYPE_SUSPENDED_OR_WITHDRAWN;
                case CHANGE_NAME:
                    return TYPE_CHANGE_NAME;
                case UPDATE_ADDRESS:
                    return TYPE_UPDATE_ADDRESS;
                default:
                    return null;
            }
        }
    }
}
