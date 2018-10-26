package com.rachev.getmydrivercardapp.utils;

public class Constants
{
    public static final String BASE_SERVER_URL = "http://192.168.0.100:8080/api/";
    
    public static final String USERS_URL_SUFFIX = "/users";
    
    public static final int BACK_PRESS_PERIOD = 2000;
    
    public static final int HTTP_STATUS_OK = 200;
    
    public static final String JSON_MEDIA_TYPE = "application/json";
    
    public static final String IS_OPENED_FROM_DRAWER = "is_origin_drawer";
    
    public static final String LOGIN_TYPE_GOOGLE = "google";
    public static final String LOGIN_TYPE_FACEBOOK = "facebook";
    
    public static final String USER_ROLE = "user";
    
    public static final String WRONG_USERNAME_OR_PASSWORD_TOAST = "Wrong username or password";
    public static final String PASSWORDS_NO_MATCH_TOAST = "Passwords don't match";
    
    public static final String NOT_ALL_FIELDS_FILLED_TOAST = "Please fill all fields";
    
    public static final String SECOND_PRESS_POPUP_TOAST = "Press once again to exit";
    
    public static final String USER_SIGNED_UP_TOAST = "User signup successfull";
    public static final String USER_LOGGED_IN_TOAST = "User logged in successfully";
    public static final String USER_LOGGED_OUT_TOAST = "User logged out successfully";
    public static final String USER_CANT_SIGNUP_TOAST = "Could not register user. It may exist...";
    
    public static final String NO_PROFILE_PIC_AVATAR_URL =
            "https://www.felixprinters.com/forum/styles/FLATBOOTS/theme/images/user4.png";
    public static final String FB_GRAPH_PROFILE_PIC_URL_PART1 = "http://graph.facebook.com/";
    public static final String FB_GRAPH_PROFILE_PIC_URL_PART2 = "/picture?type=large";
}
