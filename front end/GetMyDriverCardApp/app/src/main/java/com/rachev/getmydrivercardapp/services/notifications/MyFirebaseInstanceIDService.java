package com.rachev.getmydrivercardapp.services.notifications;

import android.preference.PreferenceManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.rachev.getmydrivercardapp.utils.Constants;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService
{
    @Override
    public void onTokenRefresh()
    {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .edit()
                .putString(Constants.Strings.FIREBASE_TOKEN, refreshedToken)
                .apply();
    }
}
