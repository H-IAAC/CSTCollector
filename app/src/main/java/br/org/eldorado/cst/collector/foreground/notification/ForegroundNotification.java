package br.org.eldorado.cst.collector.foreground.notification;

import static br.org.eldorado.cst.collector.constants.Constants.NOTIFICATION_CHANNEL_ID;
import static br.org.eldorado.cst.collector.constants.Constants.NOTIFICATION_SERVICE_ID;
import static br.org.eldorado.cst.collector.constants.Constants.STOP_SERVICE_BROADCAST;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import br.org.eldorado.cst.collector.MainActivity;
import br.org.eldorado.cst.collector.R;
import br.org.eldorado.cst.collector.ReportActivity;
import br.org.eldorado.cst.collector.foreground.receiver.ActionReceiver;

public class ForegroundNotification {

    public static Notification getNotification(Context context) {
        return getNotification(context, "");
    }

    // build a persistent notification and return it.
    private static Notification getNotification(Context context, String text) {

        // Open MainActivity
        Intent mainActivityIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder mainActivityStackBuilder = TaskStackBuilder.create(context);
        mainActivityStackBuilder.addNextIntentWithParentStack(mainActivityIntent);
        PendingIntent resultPendingIntent =
                mainActivityStackBuilder.getPendingIntent(0,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Open ReportActivity
        Intent reportActivityIntent = new Intent(context, ReportActivity.class);
        TaskStackBuilder reportActivityStackBuilder = TaskStackBuilder.create(context);
        reportActivityStackBuilder.addNextIntentWithParentStack(reportActivityIntent);
        PendingIntent reportPendingIntent =
                reportActivityStackBuilder.getPendingIntent(0,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Stop button
        Intent stopIntent = new Intent(context, ActionReceiver.class);
        stopIntent.setAction(STOP_SERVICE_BROADCAST);
        PendingIntent pStopIntent = PendingIntent.getBroadcast(context, 0, stopIntent, PendingIntent.FLAG_IMMUTABLE);

        /*
        // button
        Intent settingsIntent = new Intent(this, ActionReceiver.class);
        settingsIntent.setAction(CUSTOM_BROADCAST);
        PendingIntent pSettingsIntent = PendingIntent.getBroadcast(this, 0, settingsIntent, PendingIntent.FLAG_IMMUTABLE);
        */

        return new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)  //persistent notification!
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(text)
                .addAction(0, context.getResources().getString(R.string.stop), pStopIntent)
                .addAction(0, context.getResources().getString(R.string.settings), resultPendingIntent)
                .setAllowSystemGeneratedContextualActions(true)
                .setContentIntent(reportPendingIntent)
                .build();  //finally build and return a Notification.
    }

    /**
     * This is the method that can be called to update the Notification
     */
    public static void updateNotificationText(Context context, String text) {
        Notification notification = getNotification(context, text);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_SERVICE_ID, notification);
    }
}
