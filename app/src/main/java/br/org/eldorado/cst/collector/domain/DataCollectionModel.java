package br.org.eldorado.cst.collector.domain;

import android.content.Context;

import java.util.List;

import br.org.eldorado.cst.collector.data.db.Db;
import br.org.eldorado.cst.collector.data.db.room.dao.Entities.CollectionStats;
import br.org.eldorado.cst.collector.domain.model.CollectedData;

public class DataCollectionModel {
    private final Db db;

    public DataCollectionModel(Context context) {
        db = new Db(context);
    }

    public void insert(CollectedData data, int synced) {
        db.insert(data, synced);
    }

    public void delete(long uuid) {
        db.delete(uuid);
    }

    public List<CollectionStats> getListOfCollectedData() {
        return db.getDataCollectionStatistics();
    }

}
