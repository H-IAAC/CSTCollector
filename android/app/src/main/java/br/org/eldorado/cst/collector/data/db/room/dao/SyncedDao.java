package br.org.eldorado.cst.collector.data.db.room.dao;


import static br.org.eldorado.cst.collector.data.db.room.dao.entities.SyncedData.TABLE_NAME;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.org.eldorado.cst.collector.constants.Constants;
import br.org.eldorado.cst.collector.data.db.room.dao.entities.SyncedData;

@Dao
public interface SyncedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SyncedData data);

    @Query("DELETE FROM " + TABLE_NAME + " WHERE uuid = :uuid")
    void deleteByUuid(long uuid);

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE `uuid`=:uuid")
    SyncedData getSynced(long uuid);

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE synced = " + Constants.SYNCED_DATA.ON_GOING)
    List<SyncedData> getOnGoingStates();

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE synced = " + Constants.SYNCED_DATA.NO)
    List<SyncedData> getUnSynced();

    @Query("SELECT uuid FROM " + TABLE_NAME)
    List<Long> getAllUuids();
}
