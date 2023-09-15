package br.org.eldorado.cst.collector.data.db.room.dao;

import static br.org.eldorado.cst.collector.data.db.room.dao.Entities.LocationData.TABLE_NAME;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.org.eldorado.cst.collector.data.db.room.dao.Entities.LocationData;
import br.org.eldorado.cst.collector.data.db.room.dao.Entities.LocationStats;

@Dao
public interface LocationDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LocationData data);

    @Query("SELECT uuid, COUNT(uuid) as numberOfItems, MIN(timestamp) AS startDate, MAX(timestamp) AS endDate FROM " + TABLE_NAME + " GROUP BY uuid")
    List<LocationStats> getLocationStats();
}
