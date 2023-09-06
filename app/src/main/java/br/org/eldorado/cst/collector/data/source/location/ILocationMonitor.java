package br.org.eldorado.cst.collector.data.source.location;

import android.content.Context;

public interface ILocationMonitor {
    public boolean start(Context context);
    public boolean stop(Context context);
}
