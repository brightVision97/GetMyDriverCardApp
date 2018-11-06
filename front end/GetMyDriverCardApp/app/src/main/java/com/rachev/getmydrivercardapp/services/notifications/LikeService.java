package com.rachev.getmydrivercardapp.services.notifications;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class LikeService extends Service
{
    private static final String NOTIFICATION_ID_EXTRA = "notificationId";
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
