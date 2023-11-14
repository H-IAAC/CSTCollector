package br.org.eldorado.cst.collector.agent;

import android.content.Context;
import android.os.HandlerThread;
import android.os.Process;

import br.org.eldorado.cst.collector.agent.codelets.extrasensory.LabelsSensor;
import br.org.eldorado.cst.collector.agent.codelets.motor.GPSSender;
import br.org.eldorado.cst.collector.agent.codelets.motor.PopupGen;
import br.org.eldorado.cst.collector.agent.codelets.sensory.UniSensor;
import br.org.eldorado.cst.collector.agent.codelets.sensory.StatusSensor;
import br.org.eldorado.cst.collector.agent.codelets.sensory.UserInput;
import br.org.eldorado.cst.collector.foreground.handler.ServiceHandler;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.Mind;

public class AgentMind extends Mind {
    Context context;
    String apiURL;

    private ServiceHandler serviceHandler;
    private HandlerThread thread;

    public AgentMind (Context context, String apiURL){
        this.context = context;
        this.apiURL = apiURL;
    }



    public void mountMind(){

        //Create Memory Objects
        Memory sensorMO = createMemoryObject("SENSOR");
        Memory statusMO = createMemoryObject("STATUS");
        Memory userMO = createMemoryObject("USER_INPUT");

        //Create Codelets

        UniSensor gpsSensor = new UniSensor(context, serviceHandler);
        gpsSensor.setName("UniSensor");
        gpsSensor.addOutput(sensorMO);
        insertCodelet(gpsSensor);

        //StatusSensor statusSensor = new StatusSensor();
        //statusSensor.addOutput(statusMO);
        //insertCodelet(statusSensor);

        UserInput userInput = new UserInput();
        userInput.addOutput(userMO);
        //insertCodelet(userInput);

        LabelsSensor labelsSensor = new LabelsSensor(this.apiURL);
        labelsSensor.addInput(userMO);
        //insertCodelet(labelsSensor);


        GPSSender gpsSender = new GPSSender(this.apiURL);
        gpsSender.addInput(sensorMO);
        //gpsSender.addInput(statusMO);
        insertCodelet(gpsSender);

        PopupGen popupGen = new PopupGen(this.apiURL);
        insertCodelet(popupGen);

    }

    @Override
    public void start(){
        thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Start HandlerThread's Looper, and use it as service Handler
        serviceHandler = new ServiceHandler(this.context, thread);
        super.start();
    }

    @Override
    public void shutDown() {
        thread.quitSafely();
        super.shutDown();
    }

}
