package br.org.eldorado.cst.collector.data.api;

import com.google.gson.JsonObject;

import java.util.List;

import br.org.eldorado.cst.collector.data.api.requests.CollectedDataRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("/post")
    Call<JsonObject> postCollectedData(@Body List<CollectedDataRequest> body);
}
