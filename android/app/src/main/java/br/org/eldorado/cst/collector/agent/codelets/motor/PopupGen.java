package br.org.eldorado.cst.collector.agent.codelets.motor;


import br.unicamp.cst.io.rest.HttpCodelet;

public class PopupGen extends HttpCodelet {
    String getURL;

    public PopupGen(String apiURL){
        this.getURL = apiURL + "/pop_up";
    }
    @Override
    public void accessMemoryObjects() {

    }

    @Override
    public void calculateActivation() {

    }

    @Override
    public void proc() {

    }
}
