package br.org.eldorado.cst.collector.data.receivers.wifi;

import br.org.eldorado.cst.collector.data.receivers.battery.BatteryInfo;
import br.org.eldorado.cst.collector.model.mapper.IBattery;
import br.org.eldorado.cst.collector.model.mapper.IWifi;

public class WifiInfo implements IWifi {

    private boolean isConnected = false;

    public WifiInfo() {
    }

    public WifiInfo(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }
    @Override
    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public IWifi clone() {
        try {
            return (IWifi) super.clone();
        } catch (CloneNotSupportedException e) {
            return new WifiInfo(this.isConnected);
        }
    }
}
