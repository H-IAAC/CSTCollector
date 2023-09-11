package br.org.eldorado.cst.collector.model;

import android.content.Context;

import br.org.eldorado.cst.collector.data.receivers.BatteryMonitor;
import br.org.eldorado.cst.collector.data.receivers.LocationMonitor;
import br.org.eldorado.cst.collector.data.receivers.WifiMonitor;

public class DeviceMonitor {
    private final LocationMonitor locationMonitor;
    private final WifiMonitor wifiMonitor;
    private final BatteryMonitor batteryMonitor;

    public DeviceMonitor(LocationMonitor locationMonitor,
                         WifiMonitor wifiMonitor,
                         BatteryMonitor batteryMonitor) {
        this.locationMonitor = locationMonitor;
        this.wifiMonitor = wifiMonitor;
        this.batteryMonitor = batteryMonitor;
    }

    public void start (Context context) {
        locationMonitor.start(context);
        wifiMonitor.start(context);
        batteryMonitor.start(context);
    }

    public void stop(Context context) {
        locationMonitor.stop(context);
        wifiMonitor.stop(context);
        batteryMonitor.stop(context);
    }

    public boolean checkRules() {
        return batteryMonitor.get().isLevelHigherThan(20) &&
                wifiMonitor.get().isConnected();
    }

    public CollectedData getState() {
        return new CollectedData(locationMonitor.get(),
                                 wifiMonitor.get(),
                                 batteryMonitor.get());
    }
}
