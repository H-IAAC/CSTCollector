package br.org.eldorado.cst.collector.data.api.responses;

import com.google.gson.annotations.SerializedName;

public class StatusResponse {
    @SerializedName("status")
    public String status;

    public String getStatus() {
        return status;
    }
}
