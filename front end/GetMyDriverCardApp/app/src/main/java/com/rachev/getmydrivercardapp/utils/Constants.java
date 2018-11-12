package com.rachev.getmydrivercardapp.utils;

public class Constants
{
    public class Integers
    {
        public static final int BACK_PRESS_PERIOD = 2000;
        
        public static final int CROUTON_HEIGHT = 75;
        public static final int CROUTON_TEXT_SIZE = 16;
        public static final int CROUTON_LONG_DURATION = 4000;
        public static final int CROUTON_SHORT_DURATION = 2500;
        
        public static final int PICK_IMAGE_BITMAP_ID = 234;
        
        public static final int DEFAULT_MIN_WIDTH_QUALITY = 400;
        
        public static final int BITMAP_COMPRESS_QUALITY = 100;
        
        public static final int PICK_ID_IMAGE = 235;
        public static final int PICK_DRIVING_LIC_IMAGE = 236;
        public static final int PICK_PREV_CARD_IMAGE = 237;
        
        public static final int NOTIFICATION_RANDOM_BOUND = 60000;
    }
    
    public class Strings
    {
        public static final String BASE_SERVER_URL = "http://192.168.0.101:8080/api";
        
        public static final String FIREBASE_TOKEN = "cfpNjsjN5dU:APA91bG1FQaSX_" +
                "qgyeMX7ETlx23A9O1j8rEL0ccL7cWDK2ig4ybd0AdlUwD5xm1b8l0IMDUjSvpf7NN" +
                "0CfdQUhLS4cztEB_-iSvh3hXX2p5COIqrDnnTDC7QhbovqXB9gxrTK3yet9ob";
        
        public static final String ADMIN_CHANNEL_ID = "admin_channel";
        
        public static final String NOTIFICATION_ID_EXTRA = "notification_id";
        
        public static final String USERS_URL_SUFFIX = "/users";
        public static final String USER_ME_SUFFIX = "/me";
        public static final String REQUESTS_URL_SUFFIX = "/requests";
        public static final String ALL_REQUESTS_SUFFIX = "/getAll";
        
        public static final String TEMP_IMG_PREFIX = "temp_img";
        public static final String TEMP_IMG_SUFFIX = ".jpg";
        public static final String PNG_SUFFIX = ".png";
        
        public static final String LOGIN_TYPE_GOOGLE = "google";
        public static final String LOGIN_TYPE_FACEBOOK = "facebook";
        
        public static final String PERMISSIONS_GRANTED = "Permissions granted";
        public static final String PERMISSIONS_DENIED = "Permissions denied";
        
        public static final String NO_IMG_SELECTED = "No image selected";
        
        public static final String DATE_PATTERN = "yyyyMMdd_HHmmss";
        
        public static final String PICK_IMG = "Pick image";
        
        public static final String CONNECTION_TO_SERVER_TIMED_OUT = "No connection to the server";
        
        public static final String SIGNATURE_SAVED_AT = "Signature saved successfully at ";
        
        public static final String USER_SIGNED_UP = "User signup successful";
        public static final String USER_LOGGED_IN = "User logged in successfully";
        public static final String USER_LOGGED_OUT = "User logged out successfully";
        public static final String NOT_ALL_FIELDS_FILLED = "Please fill all fields";
        public static final String SECOND_BACK_PRESS_TO_EXIT = "Press once again to exit";
        public static final String PASSWORDS_NOT_MATCHING = "Passwords not matching";
        public static final String WRONG_USERNAME_OR_PASS = "Wrong username or password";
        public static final String USER_SIGNUP_INABILITY = "Could not register user. It may exist…";
        public static final String INCORRECT_CREDENTIALS = "Incorrect credentials";
        
        public static final String BYPASS_METHOD_NAME = "disableDeathOnFileUriExposure";
        
        public static final String FB_PROFILE_PIC_URL_PRE_ID = "http://graph.facebook.com/";
        public static final String FB_PROFILE_PIC_URL_POST_ID = "/picture?type=large";
    }
}
