package br.org.eldorado.cst.collector.domain;

import static br.org.eldorado.cst.collector.constants.Constants.TAG;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import br.org.eldorado.cst.collector.constants.Constants;
import br.org.eldorado.cst.collector.data.api.ApiInterface;
import br.org.eldorado.cst.collector.data.api.ClientApi;
import br.org.eldorado.cst.collector.data.api.requests.CollectedDataRequest;
import br.org.eldorado.cst.collector.data.db.Db;
import br.org.eldorado.cst.collector.data.db.room.dao.entities.CollectedData;
import br.org.eldorado.cst.collector.data.db.room.dao.entities.CollectionStats;
import br.org.eldorado.cst.collector.data.db.room.dao.entities.SyncedData;
import br.org.eldorado.cst.collector.domain.model.CollectionState;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataCollectionModel {
    private final Db db;
    private final ApiInterface apiInterface;
    private final SyncedModel syncedModel;

    public DataCollectionModel(Context context) {
        db = new Db(context);
        ClientApi api = new ClientApi(context);
        apiInterface = api.getClient().create(ApiInterface.class);
        syncedModel = new SyncedModel(context);
    }

    public void insert(CollectionState data, int synced) {
        db.insert(data, synced);
    }

    public void delete(long uuid) {
        db.delete(uuid);
    }

    public void sendUnSyncedData() {
        List<SyncedData> unSyncedData = db.getUnSynced();

        for (SyncedData data : unSyncedData) {
            Log.d(TAG, "Sending data from uuid: " + data.uuid + ".");
            send(data.uuid);
        }
    }

    public List<CollectedData> getNotSent(long uuid) {
        return db.getNotSent(uuid);
    }

    public int send(long uuid) {
        // Get the collected data related to the uuid
        List<CollectedData> collectedData = getNotSent(uuid);
        List<CollectedDataRequest> request = new ArrayList<>();

        if (collectedData.size() == 0) {
            updateCollectionStatus(uuid);
            return 0;
        }

        for (CollectedData data : collectedData) {
            request.add(new CollectedDataRequest(data.timestamp, data.latitude, data.longitude));
        }

        // send the data
        Call<JsonObject> call = apiInterface.postCollectedData(request);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "API onResponse: " + response.message());
                syncedModel.updateCollectedDataSend(collectedData, true);

                updateCollectionStatus(uuid);
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "API onFailure: " + t.getMessage());
                call.cancel();
            }
        });

        return collectedData.size();
    }

    private void updateCollectionStatus(long uuid) {
        int numberOfElementsNotSent = db.getNotSent(uuid).size();

        if (syncedModel.get(uuid).synced == Constants.SYNCED_DATA.NO &&
            numberOfElementsNotSent == 0)
            syncedModel.updateSynced(uuid, Constants.SYNCED_DATA.YES);
    }

    public List<CollectionStats> getListOfCollectedData() {
        return db.getDataCollectionStatistics();
    }
}
