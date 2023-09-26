package br.org.eldorado.cst.collector.foreground.receiver;

import static br.org.eldorado.cst.collector.constants.Constants.TAG;
import static br.org.eldorado.cst.collector.constants.Constants.STOP_SERVICE_BROADCAST;
import static br.org.eldorado.cst.collector.constants.Constants.CUSTOM_BROADCAST;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import br.org.eldorado.cst.collector.constants.Constants;
import br.org.eldorado.cst.collector.foreground.ForegroundService;
import br.org.eldorado.cst.collector.foreground.handler.ServiceHandler;
import br.org.eldorado.cst.collector.foreground.notification.ForegroundNotification;

public class ActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG , "ActionReceiver checking");

        if(intent == null || intent.getAction() == null)
            return;

        switch (intent.getAction()) {
            case STOP_SERVICE_BROADCAST:
                Log.d(TAG , "ActionReceiver STOP_SERVICE_BROADCAST received");

                ForegroundNotification.updateNotificationText(context, "Stop...");

                // Send message to stop Handler
                Intent stopIntent = new Intent();
                stopIntent.putExtra(Constants.HANDLER_MESSAGE.COMMAND, Constants.HANDLER_ACTION.STOP);
                ServiceHandler.sendMessage(stopIntent.getExtras());

                // Stop service
                context.stopService(new Intent(context, ForegroundService.class));

                break;

            case CUSTOM_BROADCAST:
                Log.d(TAG , "ActionReceiver CUSTOM_BROADCAST received");
                break;
            default:
                Log.d(TAG , "ActionReceiver found action: " + intent.getAction());
        }
    }

}
