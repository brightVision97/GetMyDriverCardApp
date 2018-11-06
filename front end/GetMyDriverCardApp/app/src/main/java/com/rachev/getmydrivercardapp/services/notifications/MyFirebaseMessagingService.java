package com.rachev.getmydrivercardapp.services.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.rachev.getmydrivercardapp.R;
import com.rachev.getmydrivercardapp.utils.Constants;
import com.rachev.getmydrivercardapp.views.StartActivity;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    private NotificationManager notificationManager;
    
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        Intent notificationIntent = new Intent(this, StartActivity.class);
        
        if (StartActivity.isAppRunning)
        {
        
        } else
        {
        
        }
        
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        
        final PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);
        
        int notificationId = new Random().nextInt(Constants.Integers.NOTIFICATION_RANDOM_BOUND);
        
        Intent likeIntent = new Intent(this, LikeService.class);
        likeIntent.putExtra(Constants.Strings.NOTIFICATION_ID_EXTRA, notificationId);
        PendingIntent likePendingIntent = PendingIntent.getService(this,
                notificationId + 1, likeIntent, PendingIntent.FLAG_ONE_SHOT);
        
        
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            setupChannels();
        
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, Constants.Strings.ADMIN_CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(remoteMessage.getNotification().getTitle())
                        .setContentText(remoteMessage.getNotification().getBody())
                        .setAutoCancel(true)
                        .setColor(Color.CYAN)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
        
        notificationManager.notify(notificationId, notificationBuilder.build());
    }
    
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels()
    {
        CharSequence adminChannelName = "Channel1";
        String adminChannelDescription = "Best channel";
        
        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(Constants.Strings.ADMIN_CHANNEL_ID,
                adminChannelName, NotificationManager.IMPORTANCE_LOW);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.CYAN);
        adminChannel.enableVibration(true);
        
        if (notificationManager != null)
            notificationManager.createNotificationChannel(adminChannel);
    }
}