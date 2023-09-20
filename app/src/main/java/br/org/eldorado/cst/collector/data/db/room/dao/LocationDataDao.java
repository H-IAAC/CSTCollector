package br.org.eldorado.cst.collector.data.db.room.dao;

import static br.org.eldorado.cst.collector.data.db.room.dao.Entities.LocationData.TABLE_NAME;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.org.eldorado.cst.collector.data.db.room.dao.Entities.LocationData;
import br.org.eldorado.cst.collector.data.db.room.dao.Entities.LocationStats;
import br.org.eldorado.cst.collector.data.db.room.dao.Entities.SyncedData;

@Dao
public interface LocationDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LocationData data);

    @Query("DELETE FROM " + TABLE_NAME + " WHERE uuid = :uuid")
    void deleteByUuid(long uuid);

    @Query("SELECT loc.uuid, COUNT(loc.uuid) as numberOfItems, MIN(timestamp) AS startDate, MAX(timestamp) AS endDate, sync.synced" +
            " FROM " + TABLE_NAME + " as loc," +
            SyncedData.TABLE_NAME + " as sync" +
            " WHERE loc.uuid = sync.uuid" +
            " GROUP BY loc.uuid" +
            " ORDER BY startDate DESC")
    List<LocationStats> getLocationStats();
}
