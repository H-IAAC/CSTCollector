package br.org.eldorado.cst.collector.data.db.room.dao;


import static br.org.eldorado.cst.collector.data.db.room.dao.Entities.SyncedData.TABLE_NAME;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import br.org.eldorado.cst.collector.data.db.room.dao.Entities.SyncedData;

@Dao
public interface SyncedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SyncedData data);

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE `uuid`=:uuid")
    SyncedData getSynced(long uuid);
}
