package br.org.eldorado.cst.collector.data.receivers;

import android.content.Context;

import br.org.eldorado.cst.collector.data.receivers.battery.DeviceBatteryReceiver;
import br.org.eldorado.cst.collector.data.receivers.battery.IBatteryMonitor;
import br.org.eldorado.cst.collector.domain.mapper.IBattery;

public class BatteryMonitor {

    IBatteryMonitor batteryMonitor = new DeviceBatteryReceiver();

    public boolean start(Context context) {
        batteryMonitor.start(context);
        return false;
    }

    public boolean stop(Context context) {
        batteryMonitor.stop(context);
        return false;
    }

    public IBattery get() {
        return batteryMonitor.getBattery();
    }
}
