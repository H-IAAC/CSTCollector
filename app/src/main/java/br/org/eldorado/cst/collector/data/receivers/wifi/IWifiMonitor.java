package br.org.eldorado.cst.collector.data.receivers.wifi;

import android.content.Context;

import br.org.eldorado.cst.collector.domain.mapper.IWifi;

public interface IWifiMonitor {
    public boolean start(Context context);
    public boolean stop(Context context);
    public IWifi getWifi();
}
