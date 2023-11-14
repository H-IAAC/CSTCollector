package br.org.eldorado.cst.collector.agent.codelets.sensory;

import android.os.Message;

import br.org.eldorado.cst.collector.foreground.handler.ServiceHandler;
import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;

public class StatusSensor extends Codelet {
    private Memory statusMO;

    @Override
    public void accessMemoryObjects() {
        if(statusMO == null){
            statusMO = getOutput("STATUS");
        }
    }

    @Override
    public void proc() {

        Message message = ServiceHandler.obtainMessage();
        // a ideia é capturar os dados sensoriais
        double [] value = new double[3];

        statusMO.setI(value);
    }

    @Override
    public void calculateActivation() {
    }
}
