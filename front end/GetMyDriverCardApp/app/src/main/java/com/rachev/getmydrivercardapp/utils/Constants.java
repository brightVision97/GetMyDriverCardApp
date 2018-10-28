package com.rachev.getmydrivercardapp.utils;

public class Constants
{
    public class Integers
    {
        public static final int BACK_PRESS_PERIOD = 2000;
    
        public static final int HTTP_STATUS_OK = 200;
    
        public static final int CROUTON_HEIGHT = 70;
        public static final int CROUTON_TEXT_SIZE = 15;
        public static final int CROUTON_LONG_DURATION = 4000;
        public static final int CROUTON_SHORT_DURATION = 2500;
    }
    
    public class Strings
    {
        public static final String BASE_SERVER_URL = "http://192.168.0.102:8080/api";
    
        public static final String USERS_URL_SUFFIX = "/users";
    
        public static final String JSON_MEDIA_TYPE = "application/json";
    
        public static final String LOGIN_TYPE_GOOGLE = "google";
        public static final String LOGIN_TYPE_FACEBOOK = "facebook";
    
        public static final String USER_ROLE = "user";
    
        public static final String WRONG_USERNAME_OR_PASSWORD = "Wrong username or password";
        public static final String PASSWORDS_NOT_MATCHING = "Passwords don't match";
    
        public static final String NOT_ALL_FIELDS_FILLED = "Please fill all fields";
    
        public static final String SECOND_PRESS_NOTIFIER = "Press once again to exit";
    
        public static final String USER_SIGNED_UP = "User signup successfull";
        public static final String USER_LOGGED_IN = "User logged in successfully";
        public static final String USER_LOGGED_OUT = "User logged out successfully";
        public static final String USER_COULD_NOT_SIGNUP = "Could not register user. It may exist...";
        public static final String USER_INCORRECT_CREDENTIALS = "Incorrect credentials";
    
        public static final String BLANK_PROFILE_PIC_IMG_URL =
                "https://www.felixprinters.com/forum/styles/FLATBOOTS/theme/images/user4.png";
        public static final String FB_PROFILE_PIC_URL_PRE_ID = "http://graph.facebook.com/";
        public static final String FB_PROFILE_PIC_URL_POST_ID = "/picture?type=large";
    }
}
