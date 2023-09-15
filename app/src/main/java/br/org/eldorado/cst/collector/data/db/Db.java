package br.org.eldorado.cst.collector.data.db;

import android.content.Context;

import br.org.eldorado.cst.collector.data.db.room.DataRepository;

public class Db {
    private final DataRepository dataRepository;
    public Db(Context context) {
        dataRepository = new DataRepository(context);
    }

    public DataRepository getRepository() {
        return dataRepository;
    }
}
