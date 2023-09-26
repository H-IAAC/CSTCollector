package br.org.eldorado.cst.collector.data.receivers.location;

import static br.org.eldorado.cst.collector.constants.Constants.TAG;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import br.org.eldorado.cst.collector.R;
import br.org.eldorado.cst.collector.constants.Constants;
import br.org.eldorado.cst.collector.domain.mapper.ILocation;
import br.org.eldorado.cst.collector.foreground.handler.ServiceHandler;
import br.org.eldorado.cst.collector.foreground.notification.ForegroundNotification;

public class DeviceLocationListener implements LocationListener, ILocationMonitor {
    private LocationManager locationManager;
    private final LocationInfo location = new LocationInfo();

    @Override
    public boolean start(Context context) {
        Log.d(TAG, "DeviceLocationListener starting");
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context,
                                               Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context,
                                               Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context,
                                               Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: fire an error notification
            Log.e(TAG, "Location is missing necessary permission.");
            return false;
        }

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Start with last know location
            //onLocationChanged(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3*1000, 10, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3*1000, 10, this);
        } else {
            Log.w(TAG, "GPS is disabled.");
            ForegroundNotification.updateNotificationText(context, context.getString(R.string.gps_is_disabled));
        }

        return true;
    }

    @Override
    public boolean stop(Context context) {
        Log.d(TAG, "DeviceLocationListener stop");
        if (locationManager != null)
            locationManager.removeUpdates(this);
        return true;
    }

    @Override
    public ILocation getLocation() {
        return location.clone();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.location.setTimestamp(location.getTime());
        this.location.setAccuracy(location.getAccuracy());
        this.location.setAltitude(location.getAltitude());
        this.location.setLatitude(location.getLatitude());
        this.location.setLongitude(location.getLongitude());
        this.location.setProvider(location.getProvider());

        Log.d(TAG, "onLocationChanged: " + location.getTime() + " provider:" + location.getProvider() +
                        " latitude: " + location.getLatitude() + " longitude: " + location.getLongitude() +
                        " altitude: " + location.getAltitude() + " accuracy: " + location.getAccuracy() +
                        " speed: " + location.getSpeed() +
                        " speedAccuracyMetersPerSecond: " + location.getSpeedAccuracyMetersPerSecond());

        Intent intent = new Intent();
        intent.putExtra(Constants.HANDLER_MESSAGE.COMMAND, Constants.HANDLER_ACTION.COLLECT);
        ServiceHandler.sendMessage(intent.getExtras());
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(TAG, "onProviderDisabled disabled: " + provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "onProviderEnabled enabled: " + provider);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "onStatusChanged provider: " + provider + " status changed:"
                + String.valueOf(status) + " extras: "
                + extras.getInt("satellites"));
    }
}
