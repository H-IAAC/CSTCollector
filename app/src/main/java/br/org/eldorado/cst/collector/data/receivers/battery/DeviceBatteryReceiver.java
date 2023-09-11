package br.org.eldorado.cst.collector.data.receivers.battery;

import static br.org.eldorado.cst.collector.constants.Constants.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

import br.org.eldorado.cst.collector.model.mapper.IBattery;

public class DeviceBatteryReceiver extends BroadcastReceiver implements IBatteryMonitor {

    private final BatteryInfo battery = new BatteryInfo();

    @Override
    public boolean start(Context context) {
        Log.d(TAG, "DeviceBatteryReceiver starting");

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        context.registerReceiver(this, filter);

        return false;
    }

    @Override
    public boolean stop(Context context) {
        Log.d(TAG, "DeviceBatteryReceiver stop");
        context.unregisterReceiver(this);
        return false;
    }

    @Override
    public IBattery getBattery() {
        return battery.clone();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        int batteryLevel = 0;
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int rawLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

        boolean isPresent = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);

        if (isPresent) {
            /* Battery level need to consider the battery health, scale value shows how good
             * the battery. When battery scale is 100, means its 100% ok, and each section
             * of the battery, that hold charge is working fine and capable to hold charge.
             */
            if (rawLevel >= 0 && scale > 0) {
                batteryLevel = (rawLevel * 100) / scale;
                battery.setLevel(batteryLevel);
            }

            Log.d(TAG, "Battery level: " + batteryLevel);
        }
    }

}
