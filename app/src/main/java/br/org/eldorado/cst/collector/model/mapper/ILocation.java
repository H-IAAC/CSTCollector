package br.org.eldorado.cst.collector.model.mapper;

public interface ILocation {
    long getTimestamp();
    String getProvider();
    double getLatitude();
    double getLongitude();
    double getAltitude();
    float getAccuracy();
}
