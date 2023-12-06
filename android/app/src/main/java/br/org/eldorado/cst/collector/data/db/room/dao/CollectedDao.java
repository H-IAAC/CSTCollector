package br.org.eldorado.cst.collector.data.db.room.dao;

import static br.org.eldorado.cst.collector.data.db.room.dao.entities.CollectedData.TABLE_NAME;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.org.eldorado.cst.collector.data.db.room.dao.entities.CollectedData;
import br.org.eldorado.cst.collector.data.db.room.dao.entities.CollectionStats;
import br.org.eldorado.cst.collector.data.db.room.dao.entities.SyncedData;

@Dao
public interface CollectedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CollectedData data);

    @Query("DELETE FROM " + TABLE_NAME + " WHERE uuid = :uuid")
    void deleteByUuid(long uuid);

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE uuid = :uuid")
    List<CollectedData> getByUuid(long uuid);

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE uuid = :uuid AND sent = :sent")
    List<CollectedData> getByUuid(long uuid, boolean sent);

    @Query("SELECT loc.uuid, COUNT(loc.uuid) as numberOfItems, SUM(IIF(sent = 0, 1, 0)) as numberOfItemsNotSent, MIN(timestamp) AS startDate, MAX(timestamp) AS endDate, sync.synced" +
            " FROM " + TABLE_NAME + " as loc " +
            //SyncedData.TABLE_NAME + " as sync" +
            " INNER JOIN " + SyncedData.TABLE_NAME + " as sync on sync.uuid = loc.uuid" +
            //" WHERE loc.uuid = sync.uuid" +
            " GROUP BY loc.uuid" +
            " ORDER BY startDate DESC")
    List<CollectionStats> getLocationStats();

    @Update
    void updateCollected(CollectedData collectedData);
}
