package br.org.eldorado.cst.collector.data.db;

import android.content.Context;

import java.util.List;

import br.org.eldorado.cst.collector.data.db.room.dao.CollectedDao;
import br.org.eldorado.cst.collector.data.db.room.dao.Entities.CollectedData;
import br.org.eldorado.cst.collector.data.db.room.dao.Entities.CollectionStats;
import br.org.eldorado.cst.collector.data.db.room.dao.Entities.SyncedData;
import br.org.eldorado.cst.collector.data.db.room.dao.SyncedDao;

public class Db {
    private final SyncedDao syncedDao;
    private final CollectedDao collectedDao;
    public Db(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);

        syncedDao = db.syncedDao();
        collectedDao = db.collectedDao();
    }

    public void insert(br.org.eldorado.cst.collector.domain.model.CollectedData data, int synced) {
        collectedDao.insert(new CollectedData(data.uuid,
                data.getBattery().getLevel(),
                data.getWifi().isConnected(),
                data.getLocation().getTimestamp(),
                data.getLocation().getLatitude(),
                data.getLocation().getLongitude()));
        syncedDao.insert(new SyncedData(data.uuid, synced));
    }

    public void delete(long uuid) {
        collectedDao.deleteByUuid(uuid);
        syncedDao.deleteByUuid(uuid);
    }

    public void updateSynced(long uuid, int synced) {
        syncedDao.insert(new SyncedData(uuid, synced));
    }

    public List<SyncedData> getSyncedAsOnGoing() {
        return syncedDao.getOnGoingStates();
    }

    public List<CollectionStats> getDataCollectionStatistics() {
        return collectedDao.getLocationStats();
    }

}
