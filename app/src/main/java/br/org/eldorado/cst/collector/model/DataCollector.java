package br.org.eldorado.cst.collector.model;

import static br.org.eldorado.cst.collector.constants.Constants.TAG;

import android.content.Context;
import android.util.Log;

import br.org.eldorado.cst.collector.data.receivers.BatteryMonitor;
import br.org.eldorado.cst.collector.data.receivers.LocationMonitor;
import br.org.eldorado.cst.collector.data.receivers.WifiMonitor;

public class DataCollector {

    private DeviceMonitor deviceMonitor = new DeviceMonitor(new LocationMonitor(),
                                                            new WifiMonitor(),
                                                            new BatteryMonitor());

    public void start(Context context) {
        deviceMonitor.start(context);
    }

    public void stop(Context context) {
        deviceMonitor.stop(context);
    }

    public void collect() {
        if (deviceMonitor.checkRules()) {
            Log.d(TAG, "Collecting data...");
            CollectedData data = deviceMonitor.getState();
            // TODO: store the 'data'

        } else {
            Log.d(TAG, "Rules don't match, not collecting...");
        }
    }
}
