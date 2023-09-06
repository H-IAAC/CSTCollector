package br.org.eldorado.cst.collector.data.source;

import android.content.Context;

import br.org.eldorado.cst.collector.data.source.location.DeviceLocationListener;
import br.org.eldorado.cst.collector.data.source.location.ILocationMonitor;

public class LocationMonitor implements ILocationMonitor {
    ILocationMonitor locListener = new DeviceLocationListener();
    public boolean start(Context context) {
        return locListener.start(context);
    }

    public boolean stop(Context context) {
        return locListener.stop(context);
    }
}
