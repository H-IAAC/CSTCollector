package br.org.eldorado.cst.collector.data.receivers;

import android.content.Context;

import br.org.eldorado.cst.collector.data.receivers.wifi.DeviceWifiReceiver;
import br.org.eldorado.cst.collector.data.receivers.wifi.IWifiMonitor;
import br.org.eldorado.cst.collector.domain.mapper.IWifi;

public class WifiMonitor {

    IWifiMonitor wifiMonitor = new DeviceWifiReceiver();

    public boolean start(Context context) {
        wifiMonitor.start(context);
        return false;
    }

    public boolean stop(Context context) {
        wifiMonitor.stop(context);
        return false;
    }

    public IWifi get() {
        return wifiMonitor.getWifi();
    }
}
