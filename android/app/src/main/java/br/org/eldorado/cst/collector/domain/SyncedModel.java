package br.org.eldorado.cst.collector.domain;

import android.content.Context;

import br.org.eldorado.cst.collector.constants.Constants;
import br.org.eldorado.cst.collector.data.db.Db;
import br.org.eldorado.cst.collector.data.db.room.dao.entities.SyncedData;
import br.org.eldorado.cst.collector.utils.Utils;

public class SyncedModel {

    private final Db db;

    public SyncedModel(Context context) {
        db = new Db(context);
        checkDataConsistency(context);
    }

    public void updateSynced(long uuid, int synced) {
        db.updateSynced(uuid, synced);
    }

    public void checkDataConsistency(Context context) {
        if (!Utils.isServiceRunning(context)) {
            // Set all 'on going' state to 'not synced'
            for (SyncedData data : db.getSyncedAsOnGoing()) {
                db.updateSynced(data.uuid, Constants.SYNCED_DATA.NO);
            }
        }
    }
}