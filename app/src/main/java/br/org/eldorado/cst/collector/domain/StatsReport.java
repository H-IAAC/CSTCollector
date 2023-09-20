package br.org.eldorado.cst.collector.domain;

import android.app.Application;
import android.content.Context;

import java.util.List;

import br.org.eldorado.cst.collector.data.db.Db;
import br.org.eldorado.cst.collector.data.db.room.dao.Entities.LocationStats;

public class StatsReport {
    private final Db db;

    public StatsReport(Context context) {
        db = new Db(context);
    }

    public List<LocationStats> getListOfCollectedData() {
        return db.getRepository().getLocationInfoPojo();
    }

    public void deleteCollectedData(long uuid) {
        db.getRepository().delete(uuid);
    }
}
