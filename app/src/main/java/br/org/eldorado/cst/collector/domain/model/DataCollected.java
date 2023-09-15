package br.org.eldorado.cst.collector.domain.model;

import java.util.Random;

import br.org.eldorado.cst.collector.domain.mapper.IBattery;
import br.org.eldorado.cst.collector.domain.mapper.ILocation;
import br.org.eldorado.cst.collector.domain.mapper.IWifi;

public class DataCollected {
    public final long uuid;
    private final ILocation location;
    private final IWifi wifi;
    private final IBattery battery;

    public DataCollected(long uuid, ILocation location, IWifi wifi, IBattery battery) {
        this.uuid = uuid;
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
