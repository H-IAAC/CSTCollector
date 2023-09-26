package br.org.eldorado.cst.collector.foreground.handler;

import static android.content.Context.POWER_SERVICE;
import static br.org.eldorado.cst.collector.constants.Constants.TAG;
import static br.org.eldorado.cst.collector.constants.Constants.WAKE_LOCK_TAG;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;

import br.org.eldorado.cst.collector.constants.Constants;
import br.org.eldorado.cst.collector.foreground.collector.Collector;

public class MessageHandler extends Handler {
    private final Context context;
    private Collector dataCollector;
    private final PowerManager powerManager;
    private final PowerManager.WakeLock wakeLock;

    public MessageHandler(Looper looper, Context context) {
        super(looper);
        this.context = context;
        powerManager = (PowerManager) context.getSystemService(POWER_SERVICE);
        String wakeLogTag = WAKE_LOCK_TAG + Math.random();
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, wakeLogTag);
        Log.d(TAG, "wakeLock " + wakeLogTag + " defined");
    }

    @Override
    public void handleMessage(Message msg) {
        Log.d(TAG, "should be foreground now. id number is " + msg.arg1);
        // Normally we would do some work here, like download a file.
        // For our example, we just sleep for 5 seconds then display toasts.
        //setup how many messages

        String msgCommand = "";
        Bundle extras = msg.getData();
        if (extras != null) {
            msgCommand = extras.getString(Constants.HANDLER_MESSAGE.COMMAND);
            if (msgCommand == null) return;
        }

        switch (msgCommand) {
            case Constants.HANDLER_ACTION.START:
                Log.d(TAG, "Service handler received command: " + msgCommand);
                handleStart();
                break;
            case Constants.HANDLER_ACTION.STOP:
                Log.d(TAG, "Service handler received command: " + msgCommand);
                handleStop();
                break;
            case Constants.HANDLER_ACTION.COLLECT:
                Log.d(TAG, "Service handler received command: " + msgCommand);
                dataCollector.collect();
                break;
            default:
                Log.d(TAG, "Invalid service handler received command: " + msgCommand);
        }
    }

    private void handleStop () {
        Log.d(TAG, "Service stop...");

        if (dataCollector != null)
            dataCollector.stop(context);

        if (wakeLock.isHeld()) {
            wakeLock.release();
            Log.d(TAG, "wakeLock released.");
        }
    }
    private void handleStart () {
        Log.d(TAG, "Service starting...");

        dataCollector = new Collector(context);
        dataCollector.start(context);

        if (!wakeLock.isHeld()) {
            wakeLock.acquire();
            Log.d(TAG, "wakeLock acquired.");
        } else {
            Log.w(TAG, "wakeLock not acquired as it is already held.");
        }
    }
}
