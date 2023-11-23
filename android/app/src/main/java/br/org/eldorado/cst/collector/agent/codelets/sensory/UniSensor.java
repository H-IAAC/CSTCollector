package br.org.eldorado.cst.collector.agent.codelets.sensory;

import static br.org.eldorado.cst.collector.constants.Constants.TAG;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.org.eldorado.cst.collector.data.db.Db;
import br.org.eldorado.cst.collector.data.db.room.dao.entities.CollectedData;
import br.org.eldorado.cst.collector.data.db.room.dao.entities.SyncedData;
import br.org.eldorado.cst.collector.foreground.handler.ServiceHandler;
import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;

public class UniSensor extends Codelet {
    Context context;
    String name = "UniSensor";
    //private Monitor deviceMonitor;
    private Memory sensorMO;
    private final Db db;

    //private ServiceHandler serviceHandler;
    public UniSensor(Context context, ServiceHandler serviceHandler){
        //this.context = context;
        //this.serviceHandler = serviceHandler;
        db = new Db(context);
    }

    @Override
    public void accessMemoryObjects() {
        /*deviceMonitor = new Monitor(new LocationMonitor(),
                new WifiMonitor(),
                new BatteryMonitor());
        deviceMonitor.start(context);*/
        if(sensorMO == null){
            sensorMO = getOutput("SENSOR");
        }
    }

    @Override
    public void calculateActivation() {

    }

    @Override
    public void proc() {

        List<SyncedData> unSyncedData = db.getUnSynced();
        //List<List<CollectedData>> fullData = new ArrayList<>();

        /*for (Long aLong : db.getAllUuids()) {
            List<CollectedData> collectedData = db.get(aLong);
        }*/

        /*for (SyncedData data : unSyncedData) {
            Log.d(TAG, "Sending data from uuid: " + data.uuid + ".");
            List<CollectedData> collectedData = db.get(data.uuid);

            //fullData.add(collectedData);
        }*/
        
        //sensorMO.setI(fullData);
        //sensorMO.setI(unSyncedData);
        ;
        if(!unSyncedData.isEmpty()){
            sensorMO.setI(db.get(unSyncedData.get(unSyncedData.size()-1).uuid));
        }
        else{sensorMO.setI(null);}

        //sensorMO.setI(db.get(db.getAllUuids().get(db.getAllUuids().size()-1)));

    }

    /*public Monitor getDeviceMonitor() {
        //return deviceMonitor;
    }*/

    /**
     * Tells this codelet to stop looping (stops running)
     */
    /*@Override
    public synchronized void stop() {
        //deviceMonitor.stop(context);
        super.stop();

    }*/




}
