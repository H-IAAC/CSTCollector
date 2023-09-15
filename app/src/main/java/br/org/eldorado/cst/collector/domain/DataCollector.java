package br.org.eldorado.cst.collector.domain;

import static br.org.eldorado.cst.collector.constants.Constants.TAG;

import android.content.Context;
import android.util.Log;

import br.org.eldorado.cst.collector.data.db.Db;
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
    }

    public void stop(Context context) {
        deviceMonitor.stop(context);
    }

    public void collect() {
        if (deviceMonitor.checkRules()) {
            Log.d(TAG, "Collecting data...");
            DataCollected data = deviceMonitor.getState();
            // TODO: store the 'data'
            db.getRepository().insert(data);

            //Log.d(TAG, "TOTAL ---> " + db.getRepository().getLocationInfoPojo().numberOfItems + " " +
            //        "startDate: " + db.getRepository().getLocationInfoPojo().startDate +
            //        "endDate: " + db.getRepository().getLocationInfoPojo().endDate);

        } else {
            Log.d(TAG, "Rules don't match, not collecting...");
        }
    }

}
