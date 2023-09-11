package br.org.eldorado.cst.collector.data.receivers.location;

import android.content.Context;

import br.org.eldorado.cst.collector.model.mapper.ILocation;

public interface ILocationMonitor {
    public boolean start(Context context);
    public boolean stop(Context context);
    public ILocation getLocation();
}
