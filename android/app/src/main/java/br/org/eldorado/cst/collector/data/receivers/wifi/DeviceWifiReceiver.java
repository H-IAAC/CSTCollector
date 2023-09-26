package br.org.eldorado.cst.collector.data.receivers.wifi;

import static br.org.eldorado.cst.collector.constants.Constants.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.util.Log;

import br.org.eldorado.cst.collector.domain.mapper.IWifi;

public class DeviceWifiReceiver extends BroadcastReceiver implements IWifiMonitor {

    private final WifiInfo wifi = new WifiInfo();

    @Override
    public boolean start(Context context) {
        Log.d(TAG, "DeviceWifiReceiver starting");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        context.registerReceiver(this, intentFilter);
        return true;
    }

    @Override
    public boolean stop(Context context) {
        Log.d(TAG, "DeviceWifiReceiver stop");
        context.unregisterReceiver(this);
        return true;
    }

    @Override
    public IWifi getWifi() {
        return wifi.clone();
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {

        Log.d(TAG , "DeviceWifiReceiver checking");

        if(intent == null || intent.getExtras() == null || intent.getAction() == null)
            return;

        switch (intent.getAction()) {
            case WifiManager.WIFI_STATE_CHANGED_ACTION:
                Log.d(TAG, "WIFI_STATE_CHANGED_ACTION");

                int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);

                switch (wifiState) {
                    case WifiManager.WIFI_STATE_ENABLED:
                        Log.d(TAG, "Wifi state is enabled");
                        wifi.setIsConnected(true);
                        break;
                    case WifiManager.WIFI_STATE_DISABLED:
                        Log.d(TAG, "Wifi state is disabled");
                        wifi.setIsConnected(false);
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                        Log.d(TAG, "Wifi state is disabling");
                        wifi.setIsConnected(false);
                        break;
                    case WifiManager.WIFI_STATE_ENABLING:
                        Log.d(TAG, "Wifi state is enabling");
                        wifi.setIsConnected(false);
                        break;
                    case WifiManager.WIFI_STATE_UNKNOWN:
                        Log.d(TAG, "Wifi state is unknown");
                        wifi.setIsConnected(false);
                        break;
                    default:
                        Log.d(TAG, "Wifi state is invalid");
                        wifi.setIsConnected(false);
                }

                break;
            default:
                Log.d(TAG, "DeviceWifiReceiver received: " + intent.getAction());
        }
    }
}
