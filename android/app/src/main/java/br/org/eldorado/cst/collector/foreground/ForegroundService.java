package br.org.eldorado.cst.collector.foreground;

import static br.org.eldorado.cst.collector.constants.Constants.NOTIFICATION_SERVICE_ID;
import static br.org.eldorado.cst.collector.constants.Constants.TAG;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Process;

import android.util.Log;
import android.widget.Toast;

import br.org.eldorado.cst.collector.foreground.handler.ServiceHandler;
import br.org.eldorado.cst.collector.foreground.notification.ForegroundNotification;


/**
 * This is the background service that prompts itself to a foreground service with a persistent
 * notification. It is required since Oreo otherwise, a background service without an app will be killed.
 */

public class ForegroundService extends Service {

    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";
    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";
    public static Boolean isRunning = false;
    private ServiceHandler serviceHandler;
    private HandlerThread thread;

    @Override
    public void onCreate() {
        //promote to foreground and create persistent notification.
        //in Oreo we only have a few seconds to do this or the service is killed.
        Notification notification = ForegroundNotification.getNotification(getApplicationContext());

        startForeground(NOTIFICATION_SERVICE_ID, notification);

        // Start up the thread running the service. Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        thread = new HandlerThread("ServiceStartArguments",
                                                 Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Start HandlerThread's Looper, and use it as service Handler
        serviceHandler = new ServiceHandler(getApplicationContext(), thread);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand with startId: " + startId);

        if (intent != null) {
            Log.d(TAG, "onStartCommand: " + intent.toString());
            String action = intent.getAction();
            if(action!=null) {

                switch (action) {
                    case ACTION_START_FOREGROUND_SERVICE:
                        ForegroundService.isRunning = true;
                        ServiceHandler.sendMessage(intent.getExtras());
                        break;
                }
            }
        } else {
            Toast.makeText(ForegroundService.this, "The Intent to start is null?!", Toast.LENGTH_SHORT).show();
        }

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Service destroy");
        thread.quitSafely();
        ForegroundService.isRunning = false;
        Toast.makeText(this, "CST Collect finish", Toast.LENGTH_SHORT).show();
    }
}
