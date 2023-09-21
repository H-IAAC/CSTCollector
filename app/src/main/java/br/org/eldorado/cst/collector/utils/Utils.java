package br.org.eldorado.cst.collector.utils;

import android.app.ActivityManager;
import android.content.Context;

import br.org.eldorado.cst.collector.foreground.ForegroundService;

public class Utils {

    public static boolean isServiceRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (ForegroundService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
