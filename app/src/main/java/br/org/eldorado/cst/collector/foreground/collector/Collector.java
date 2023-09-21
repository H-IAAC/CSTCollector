package br.org.eldorado.cst.collector.foreground.collector;

import static br.org.eldorado.cst.collector.constants.Constants.TAG;

import android.content.Context;
import android.util.Log;

import br.org.eldorado.cst.collector.constants.Constants;
import br.org.eldorado.cst.collector.data.receivers.BatteryMonitor;
import br.org.eldorado.cst.collector.data.receivers.LocationMonitor;
import br.org.eldorado.cst.collector.data.receivers.WifiMonitor;
import br.org.eldorado.cst.collector.domain.DataCollectionModel;
import br.org.eldorado.cst.collector.domain.SyncedModel;
import br.org.eldorado.cst.collector.domain.model.CollectedData;

public class Collector {

    private Monitor deviceMonitor = new Monitor(new LocationMonitor(),
                                                new WifiMonitor(),
                                                new BatteryMonitor());
    private final DataCollectionModel collectionModel;
    private final SyncedModel syncedModel;

    public Collector(Context context) {
        syncedModel = new SyncedModel(context);
        collectionModel = new DataCollectionModel(context);
    }

    public void start(Context context) {
        deviceMonitor.start(context);
    }

    public void stop(Context context) {
        deviceMonitor.stop(context);
        syncedModel.updateSynced(deviceMonitor.uuid, Constants.SYNCED_DATA.NO);
    }

    public void collect() {
        if (deviceMonitor.checkRules()) {
            Log.d(TAG, "Collecting data...");
            CollectedData data = deviceMonitor.getState();

            // When starting Location Service, Android may return invalid GPS data
            // with timestamp as '0'. We need to check if data is valid, and discard it
            // when timestamp is '0'.
            if (data.getLocation().getTimestamp() == 0) {
                Log.w(TAG, "Data timestamp is '0'. Ignoring this data. ");
                return;
            }

            collectionModel.insert(data, Constants.SYNCED_DATA.ON_GOING);

            //Log.d(TAG, "TOTAL ---> " + db.getRepository().getLocationInfoPojo().numberOfItems + " " +
            //        "startDate: " + db.getRepository().getLocationInfoPojo().startDate +
            //        "endDate: " + db.getRepository().getLocationInfoPojo().endDate);

        } else {
            Log.d(TAG, "Rules don't match, not collecting...");
        }
    }

}
