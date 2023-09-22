package br.org.eldorado.cst.collector.data.api.requests;

public class CollectedDataRequest {
    final long timestamp;
    final double latitude;
    final double longitude;

    public CollectedDataRequest(long timestamp, double latitude, double longitude) {
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
