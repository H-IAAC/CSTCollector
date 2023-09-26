package br.org.eldorado.cst.collector.data.db.room.dao.entities;

import static br.org.eldorado.cst.collector.data.db.room.dao.entities.CollectedData.TABLE_NAME;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = TABLE_NAME)
public class CollectedData {
    public static final String TABLE_NAME = "Collected";
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int _id;

    @NonNull
    @ColumnInfo(name = "uuid")
    public long uuid;

    @NonNull
    @ColumnInfo(name = "batteryLevel")
    public int batteryLevel;

    @NonNull
    @ColumnInfo(name = "wifiState")
    public boolean wifiState;

    @NonNull
    @ColumnInfo(name = "timestamp")
    public long timestamp;

    @NonNull
    @ColumnInfo(name = "latitude")
    public double latitude;

    @NonNull
    @ColumnInfo(name = "longitude")
    public double longitude;

    public CollectedData(@NonNull long uuid, int batteryLevel, boolean wifiState, long timestamp, double latitude, double longitude) {
        this.uuid = uuid;
        this.batteryLevel = batteryLevel;
        this.wifiState = wifiState;
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
