package br.org.eldorado.cst.collector.foreground.collector;

import static br.org.eldorado.cst.collector.constants.Constants.TAG;

import android.content.Context;
import android.util.Log;

import br.org.eldorado.cst.collector.agent.AgentMind;
import br.org.eldorado.cst.collector.agent.codelets.sensory.UniSensor;
import br.org.eldorado.cst.collector.constants.Constants;
import br.org.eldorado.cst.collector.data.receivers.BatteryMonitor;
import br.org.eldorado.cst.collector.data.receivers.LocationMonitor;
import br.org.eldorado.cst.collector.data.receivers.WifiMonitor;
import br.org.eldorado.cst.collector.domain.DataCollectionModel;
import br.org.eldorado.cst.collector.domain.SyncedModel;
import br.org.eldorado.cst.collector.domain.model.CollectionState;
import br.unicamp.cst.core.entities.Codelet;

public class Collector {

    private Monitor deviceMonitor;
    private final DataCollectionModel collectionModel;
    private final SyncedModel syncedModel;
    //private AgentMind agentMind;

    public Collector(Context context) {
        syncedModel = new SyncedModel(context);
        collectionModel = new DataCollectionModel(context);
        //agentMind = new AgentMind(context, "127.0.0.1");
    }

    public void start(Context context) {
        deviceMonitor = new Monitor(new LocationMonitor(),
                                    new WifiMonitor(),
                                    new BatteryMonitor());
        deviceMonitor.start(context);

        //agentMind.mountMind();
        //agentMind.start();
    }

    public void stop(Context context) {
        deviceMonitor.stop(context);
        //agentMind.shutDown();
        //UniSensor cod = (UniSensor) agentMind.getCodeRack().getAllCodelets().stream().filter(codelet -> codelet.getName().equals("UniSensor")).findAny().orElse(null);

        //assert cod != null;
        long uuid = deviceMonitor.uuid;
        if(collectionModel.getNotSent(uuid).size() == 0)
            syncedModel.updateSynced(deviceMonitor.uuid, Constants.SYNCED_DATA.YES);
        else
            syncedModel.updateSynced(deviceMonitor.uuid, Constants.SYNCED_DATA.NO);
        //syncedModel.updateSynced(cod.getDeviceMonitor().uuid, Constants.SYNCED_DATA.NO);
    }

    public void collect() {
        //UniSensor cod = (UniSensor) agentMind.getCodeRack().getAllCodelets().stream().filter(codelet -> codelet.getName().equals("UniSensor")).findAny().orElse(null);

        //assert cod != null;
        Log.d(TAG, "Collecting data...");
        CollectionState data = deviceMonitor.getState();
        //CollectionState data = cod.getDeviceMonitor().getState();

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
    }

}
