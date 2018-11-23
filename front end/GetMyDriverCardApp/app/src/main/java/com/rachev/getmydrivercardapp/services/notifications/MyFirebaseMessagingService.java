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
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.rachev.getmydrivercardapp.R;
import com.rachev.getmydrivercardapp.utils.Constants;
import com.rachev.getmydrivercardapp.utils.enums.RequestStatus;
import com.rachev.getmydrivercardapp.views.cardrequest.lists.RequestsListsActivity;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    private NotificationManager notificationManager;
    
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        LocalBroadcastManager broadcaster = LocalBroadcastManager.getInstance(getBaseContext());
        
        sendNotification(remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody());
        
        Intent intent = new Intent("REQUEST_ACCEPT");
        intent.putExtra("requestId", Long.valueOf(remoteMessage.getData().get("requestId")));
        intent.putExtra("requestStatus", RequestStatus.valueOf(remoteMessage.getData().get("requestStatus")));
        broadcaster.sendBroadcast(intent);
        
    }
    
    private void sendNotification(String messageTitle, String messageBody)
    {
        Intent intent = new Intent(this, RequestsListsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, intent, PendingIntent.FLAG_ONE_SHOT);
        
        String channelId = "Channel";
        int notificationId = new Random().nextInt(Constants.Integers.NOTIFICATION_RANDOM_BOUND);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, Constants.Strings.ADMIN_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_shortcut_playlist_add_check)
                        .setContentTitle(messageTitle)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setColor(Color.CYAN)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
        
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Request approval channel",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        
        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}