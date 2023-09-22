package br.org.eldorado.cst.collector.domain;

import static br.org.eldorado.cst.collector.constants.Constants.TAG;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import br.org.eldorado.cst.collector.data.api.ApiInterface;
import br.org.eldorado.cst.collector.data.api.ClientApi;
import br.org.eldorado.cst.collector.data.api.requests.CollectedDataRequest;
import br.org.eldorado.cst.collector.data.db.Db;
import br.org.eldorado.cst.collector.data.db.room.dao.Entities.CollectedData;
import br.org.eldorado.cst.collector.data.db.room.dao.Entities.CollectionStats;
import br.org.eldorado.cst.collector.domain.model.CollectionState;
import br.org.eldorado.cst.collector.utils.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataCollectionModel {
    private final Db db;
    private final ApiInterface apiInterface;

    public DataCollectionModel(Context context) {
        db = new Db(context);
        ClientApi api = new ClientApi(context);
        apiInterface = api.getClient().create(ApiInterface.class);;
    }

    public void insert(CollectionState data, int synced) {
        db.insert(data, synced);
    }

    public void delete(long uuid) {
        db.delete(uuid);
    }

    public void send(long uuid) {
        // Get the collected data related to the uuid
        List<CollectedData> collectedData = db.get(uuid);
        List<CollectedDataRequest> request = new ArrayList<CollectedDataRequest>();

        for (CollectedData data : collectedData) {
            request.add(new CollectedDataRequest(data.timestamp, data.latitude, data.longitude));
        }

        // send the data
        Call<JsonObject> call = apiInterface.postCollectedData(request);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "API onResponse: " + response.message());
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "API onFailure: " + t.getMessage());
                call.cancel();
            }
        });
    }

    public List<CollectionStats> getListOfCollectedData() {
        return db.getDataCollectionStatistics();
    }

}
