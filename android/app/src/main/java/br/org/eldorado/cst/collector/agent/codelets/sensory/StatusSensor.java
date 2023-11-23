package br.org.eldorado.cst.collector.agent.codelets.sensory;

import android.os.Message;

import java.util.ArrayList;
import java.util.Date;

import br.org.eldorado.cst.collector.data.db.room.dao.entities.CollectedData;
import br.org.eldorado.cst.collector.foreground.handler.ServiceHandler;
import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;

public class StatusSensor extends Codelet {
    private Memory statusMO;
    private Memory sensorMO;

    boolean fulfillConditions;

    Date d1 = null;

    @Override
    public void accessMemoryObjects() {
        if(sensorMO == null){
            sensorMO = getInput("SENSOR");
        }
        if(statusMO == null){
            statusMO = getOutput("STATUS");
        }
    }

    @Override
    public void proc() {
        ArrayList<CollectedData> data = (ArrayList) sensorMO.getI();
        if(data != null){
            if(!data.isEmpty()) {
                //List<List<CollectedData>> fullData = (List<List<CollectedData>>) sensorMO.getI();
                CollectedData collectedData = data.get(data.size()-1);
                //String response = " API POST request failed!";
                fulfillConditions = false;
                if (d1 == null) {
                    d1 = new Date();
                    fulfillConditions = true;
                }
                //System.out.println(response);
                if (checkConditions(collectedData)) {
                    fulfillConditions = true;
                }
                statusMO.setI(fulfillConditions);
            }
        }
    }

    private boolean checkConditions(CollectedData collectedData){
        boolean answer = false;


        Date d2 = new Date();
        // Get msec from each, and subtract.
        long diff = d2.getTime() - d1.getTime();

        long diffMinutes = diff / (60 * 1000);

        //System.out.println("Time in minutes: " + diffMinutes + " minutes.");


        if(collectedData.batteryLevel > 35 && collectedData.wifiState && diffMinutes >= 10){
            answer = true;

            d1 = new Date();
        }

        return answer;
    }


    @Override
    public void calculateActivation() {
    }
}
