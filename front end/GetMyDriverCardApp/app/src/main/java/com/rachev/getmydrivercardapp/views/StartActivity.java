package com.rachev.getmydrivercardapp.views;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.rachev.getmydrivercardapp.R;

public class StartActivity extends AppCompatActivity
{
    public static boolean isAppRunning;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel("1",
                    "Channel 1", NotificationManager.IMPORTANCE_HIGH);
            
            notificationChannel.setDescription("This is Channel 1");
            notificationChannel.setLightColor(Color.CYAN);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel);
            
            NotificationChannel notificationChannel2 = new NotificationChannel("2",
                    "Channel 2", NotificationManager.IMPORTANCE_MIN);
            
            notificationChannel.setDescription("This is Channel 2");
            notificationChannel.setLightColor(Color.CYAN);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel2);
        }
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        isAppRunning = false;
    }
}
