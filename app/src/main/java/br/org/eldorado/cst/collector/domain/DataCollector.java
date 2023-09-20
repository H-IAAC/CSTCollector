package br.org.eldorado.cst.collector.domain;

import static br.org.eldorado.cst.collector.constants.Constants.TAG;

import android.content.Context;
import android.util.Log;

import br.org.eldorado.cst.collector.constants.Constants;
import br.org.eldorado.cst.collector.data.db.Db;
import br.org.eldorado.cst.collector.data.db.room.dao.Entities.SyncedData;
import br.org.eldorado.cst.collector.data.receivers.BatteryMonitor;
import br.org.eldorado.cst.collector.data.receivers.LocationMonitor;
import br.org.eldorado.cst.collector.data.receivers.WifiMonitor;
import br.org.eldorado.cst.collector.domain.model.DataCollected;

public class DataCollector {

    private DeviceMonitor deviceMonitor = new DeviceMonitor(new LocationMonitor(),
                                                            new WifiMonitor(),
                                                            new BatteryMonitor());
    private Db db;

    public void start(Context context) {
        deviceMonitor.start(context);
        db = new Db(context);

        // Set all 'on going' state to 'not synced'
        for (SyncedData data : db.getRepository().getAllOnGoing()) {
            db.getRepository().updateSynced(data.uuid, Constants.SYNCED_DATA.NO);
        }
    }

    public void stop(Context context) {
        deviceMonitor.stop(context);
        db.getRepository().updateSynced(deviceMonitor.uuid, Constants.SYNCED_DATA.NO);
    }

    public void collect() {
        if (deviceMonitor.checkRules()) {
            Log.d(TAG, "Collecting data...");
            DataCollected data = deviceMonitor.getState();
            // TODO: store the 'data'
            db.getRepository().insert(data, Constants.SYNCED_DATA.ON_GOING);

            //Log.d(TAG, "TOTAL ---> " + db.getRepository().getLocationInfoPojo().numberOfItems + " " +
            //        "startDate: " + db.getRepository().getLocationInfoPojo().startDate +
            //        "endDate: " + db.getRepository().getLocationInfoPojo().endDate);

        } else {
            Log.d(TAG, "Rules don't match, not collecting...");
        }
    }

}
