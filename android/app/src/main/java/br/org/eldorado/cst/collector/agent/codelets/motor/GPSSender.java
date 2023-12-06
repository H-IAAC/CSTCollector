package br.org.eldorado.cst.collector.agent.codelets.motor;

import static br.org.eldorado.cst.collector.constants.Constants.TAG;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.org.eldorado.cst.collector.data.db.room.dao.entities.CollectedData;
import br.org.eldorado.cst.collector.data.db.room.dao.entities.SyncedData;
import br.org.eldorado.cst.collector.domain.DataCollectionModel;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.io.rest.HttpCodelet;

public class GPSSender extends HttpCodelet {
    String postURL;
    Memory sensorMO;
    Memory statusMO;
    HashMap<String, String> params = new HashMap<>();

    DataCollectionModel dataCollectionModel;
    public GPSSender(String apiURL, Context context){
        this.postURL = apiURL; // + "/gps_data";
        dataCollectionModel = new DataCollectionModel(context);
    }
    @Override
    public void accessMemoryObjects() {
        if(sensorMO == null){
            sensorMO = getInput("SENSOR");
        }
        if(statusMO == null){
            statusMO = getInput("STATUS");
        }
        params.put("gps_data", "");
    }

    @Override
    public void calculateActivation() {

    }

    @Override
    public void proc() {
        if(statusMO.getI() != null) {
            if ((boolean) statusMO.getI()) {
                //List<List<CollectedData>> fullData = (List<List<CollectedData>>) sensorMO.getI();
                ArrayList<CollectedData> fullData = (ArrayList<CollectedData>) sensorMO.getI();
                //String response = " API POST request failed!";
                if (fullData != null) {

                    for (CollectedData data : fullData) {
                        Log.d(TAG, "Sending data from uuid: " + data.uuid + ".");
                        dataCollectionModel.send(data.uuid);
                    }
                    //dsend();

                    sensorMO.setI(null);
                    //System.out.println(response);
                }
            }
        }

    }
}
