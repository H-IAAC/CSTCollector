package br.org.eldorado.cst.collector.foreground.collector;

import android.content.Context;

import java.util.Random;

import br.org.eldorado.cst.collector.data.receivers.BatteryMonitor;
import br.org.eldorado.cst.collector.data.receivers.LocationMonitor;
import br.org.eldorado.cst.collector.data.receivers.WifiMonitor;
import br.org.eldorado.cst.collector.domain.model.CollectionState;

public class Monitor {

    // Every time when Device Monitor start, it creates a different 'uuid', it is used to relate the
    // collected data, into the same monitor.
    public final long uuid = new Random().nextLong() & 0x3FFFFFFFFFFFFFFFL;
    private final LocationMonitor locationMonitor;
    private final WifiMonitor wifiMonitor;
    private final BatteryMonitor batteryMonitor;

    public Monitor(LocationMonitor locationMonitor,
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

    public CollectionState getState() {
        return new CollectionState(this.uuid,
                                 locationMonitor.get(),
                                 wifiMonitor.get(),
                                 batteryMonitor.get());
    }
}
