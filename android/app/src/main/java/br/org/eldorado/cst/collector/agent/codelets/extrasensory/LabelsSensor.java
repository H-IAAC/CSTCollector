package br.org.eldorado.cst.collector.agent.codelets.extrasensory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.io.rest.HttpCodelet;

public class LabelsSensor extends HttpCodelet {
    String postURL;
    Memory userInput;
    HashMap<String, String> params = new HashMap<>();

    public LabelsSensor(String apiURL){
        this.postURL = apiURL + "/labeled_locations";
    }
    @Override
    public void accessMemoryObjects() {
        if(userInput == null){
            userInput = getInput("USER_INPUT");
        }
        params.put("labeled_locations", "");
    }

    @Override
    public void calculateActivation() {

    }

    @Override
    public void proc() {

        if(userInput.getEvaluation() == 1.0){
            List request = (List) userInput.getI();
            String response = " API POST request failed!";
            if(request != null){
                String json = new Gson().toJson(request );
                params.replace("labeled_locations", json);
                String paramsString = prepareParams(params);
                //System.out.println(paramsString);
                try{
                    response = this.sendPOST(this.postURL, paramsString, null);
                }catch (IOException e){e.printStackTrace();}

                userInput.setI(null);
                System.out.println(response);
            }
        }

    }
}
