package br.org.eldorado.cst.collector.model;

import br.org.eldorado.cst.collector.model.mapper.IBattery;
import br.org.eldorado.cst.collector.model.mapper.ILocation;
import br.org.eldorado.cst.collector.model.mapper.IWifi;

public class CollectedData {

    private final ILocation location;
    private final IWifi wifi;
    private final IBattery battery;

    public CollectedData (ILocation location, IWifi wifi, IBattery battery) {
        this.location = location;
        this.wifi = wifi;
        this.battery = battery;
    }

    public ILocation getLocation() {
        return location;
    }

    public IWifi getWifi() {
        return wifi;
    }

    public IBattery getBattery() {
        return battery;
    }
}
