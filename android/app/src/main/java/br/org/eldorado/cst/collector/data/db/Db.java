package br.org.eldorado.cst.collector.data.db;

import static br.org.eldorado.cst.collector.constants.Constants.TAG;

import android.content.Context;
import android.util.Log;

import java.util.List;

import br.org.eldorado.cst.collector.data.db.room.dao.CollectedDao;
import br.org.eldorado.cst.collector.data.db.room.dao.entities.CollectedData;
import br.org.eldorado.cst.collector.data.db.room.dao.entities.CollectionStats;
import br.org.eldorado.cst.collector.data.db.room.dao.entities.SyncedData;
import br.org.eldorado.cst.collector.data.db.room.dao.SyncedDao;
import br.org.eldorado.cst.collector.domain.model.CollectionState;

public class Db {
    private final SyncedDao syncedDao;
    private final CollectedDao collectedDao;
    public Db(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);

        syncedDao = db.syncedDao();
        collectedDao = db.collectedDao();
    }

    public void insert(CollectionState data, int synced) {
        // Check it data is not already stored, it happens when android sent
        // repeated broadcast for same data.
        if (itExits(data.uuid,
                    data.getBattery().getLevel(),
                    data.getWifi().isConnected(),
                    data.getLocation().getTimestamp(),
                    data.getLocation().getLatitude(),
                    data.getLocation().getLongitude()).size() == 0) {
            // As size is 0, this data is new, so insert it.
            collectedDao.insert(new CollectedData(data.uuid,
                    data.getBattery().getLevel(),
                    data.getWifi().isConnected(),
                    data.getLocation().getTimestamp(),
                    data.getLocation().getLatitude(),
                    data.getLocation().getLongitude(),
                    false));
            syncedDao.insert(new SyncedData(data.uuid, synced));
        } else {
            Log.i(TAG, "Ignoring repeated data.");
        }
    }

    public void delete(long uuid) {
        collectedDao.deleteByUuid(uuid);
        syncedDao.deleteByUuid(uuid);
    }

    public List<CollectedData> get(long uuid) {
         return collectedDao.getByUuid(uuid);
    }

    public List<CollectedData> itExits(long uuid, int batteryLevel, boolean wifiState, long timestamp, double latitude, double longitude) {
        return collectedDao.itExists(uuid, batteryLevel, wifiState, timestamp, latitude, longitude);
    }

    public SyncedData getSyncedData(long uuid) {
        return syncedDao.getSynced(uuid);
    }

    public List<CollectedData> getNotSent(long uuid) {
        return collectedDao.getByUuid(uuid, false);
    }

    public void updateCollectedDataSend(List<CollectedData> collectedData, boolean sendStatus) {
        for (CollectedData data : collectedData) {
            data.sent = sendStatus;
            collectedDao.updateCollected(data);
        }
    }

    public void updateSynced(long uuid, int synced) {
        syncedDao.insert(new SyncedData(uuid, synced));
    }

    public List<SyncedData> getOnGoing() {
        return syncedDao.getOnGoingStates();
    }

    public List<SyncedData> getUnSynced() {
        return syncedDao.getUnSynced();
    }

    public List<CollectionStats> getDataCollectionStatistics() {
        return collectedDao.getLocationStats();
    }

    public List<Long>  getAllUuids() {
        return syncedDao.getAllUuids();
    }

}
