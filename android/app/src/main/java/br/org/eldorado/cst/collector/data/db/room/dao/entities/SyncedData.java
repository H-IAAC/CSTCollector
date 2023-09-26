package br.org.eldorado.cst.collector.data.db.room.dao.entities;

import static br.org.eldorado.cst.collector.data.db.room.dao.entities.SyncedData.TABLE_NAME;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = TABLE_NAME,
        indices = {@Index(value = {"uuid"}, unique = true)})
public class SyncedData {
    public static final String TABLE_NAME = "Sync";

    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int _id;

    @NonNull
    @ColumnInfo(name = "uuid")
    public long uuid;

    @NonNull
    @ColumnInfo(name = "synced")
    public int synced;

    public SyncedData(@NonNull long uuid, int synced) {
        this.uuid = uuid;
        this.synced = synced;
    }
}