package br.org.eldorado.cst.collector.data.receivers.battery;

import android.content.Context;

import br.org.eldorado.cst.collector.domain.mapper.IBattery;

public interface IBatteryMonitor {
    public boolean start(Context context);
    public boolean stop(Context context);
    public IBattery getBattery();
}
