package br.org.eldorado.cst.collector.data.receivers;

import android.content.Context;
import android.location.LocationListener;

import br.org.eldorado.cst.collector.data.receivers.location.DeviceLocationListener;
import br.org.eldorado.cst.collector.data.receivers.location.ILocationMonitor;
import br.org.eldorado.cst.collector.model.mapper.ILocation;

public class LocationMonitor {

    private final ILocationMonitor locationListener = new DeviceLocationListener();

    public boolean start(Context context) {
        return locationListener.start(context);
    }

    public boolean stop(Context context) {
        return locationListener.stop(context);
    }
    public ILocation get() {
        return locationListener.getLocation();
    }
}
