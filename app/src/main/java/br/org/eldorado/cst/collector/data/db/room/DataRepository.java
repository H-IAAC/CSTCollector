package br.org.eldorado.cst.collector.data.db.room;

import android.content.Context;

import java.util.List;

import br.org.eldorado.cst.collector.data.db.room.dao.Entities.LocationData;
import br.org.eldorado.cst.collector.data.db.room.dao.Entities.LocationStats;
import br.org.eldorado.cst.collector.data.db.room.dao.Entities.SyncedData;
import br.org.eldorado.cst.collector.data.db.room.dao.LocationDataDao;
import br.org.eldorado.cst.collector.data.db.room.dao.SyncedDao;
import br.org.eldorado.cst.collector.domain.model.DataCollected;

public class DataRepository {
    private LocationDataDao locationDao;
    private SyncedDao syncedDao;

    public DataRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        locationDao = db.locationDataDao();
        syncedDao = db.syncedDao();
    }

    public void insert(DataCollected data) {
        locationDao.insert(new LocationData(data.uuid,
                                        data.getBattery().getLevel(),
                                        data.getWifi().isConnected(),
                                        data.getLocation().getTimestamp(),
                                        data.getLocation().getLatitude(),
                                        data.getLocation().getLongitude()));
        syncedDao.insert(new SyncedData(data.uuid, false));
    }

    public List<LocationStats> getLocationInfoPojo() {
        return locationDao.getLocationStats();
    }
}
