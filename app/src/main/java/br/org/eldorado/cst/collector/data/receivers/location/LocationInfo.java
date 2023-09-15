package br.org.eldorado.cst.collector.data.receivers.location;

import br.org.eldorado.cst.collector.domain.mapper.ILocation;

public class LocationInfo implements ILocation {

    private long timestamp;
    private String provider;
    private double latitude;
    private double longitude;
    private double altitude;
    private float accuracy;

    public LocationInfo() {

    }

    public LocationInfo(long timestamp, String provider, double latitude,
                        double longitude, double altitude, float accuracy) {
        this.timestamp = timestamp;
        this.provider = provider;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.accuracy = accuracy;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String getProvider() {
        return provider;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public double getAltitude() {
        return altitude;
    }

    @Override
    public float getAccuracy() {
        return accuracy;
    }

    @Override
    public ILocation clone() {
        try {
            return (ILocation) super.clone();
        } catch (CloneNotSupportedException e) {
            return new LocationInfo(this.timestamp,
                                    this.provider,
                                    this.latitude,
                                    this.longitude,
                                    this.altitude,
                                    this.accuracy);
        }
    }
}
