package br.org.eldorado.cst.collector.data.db;

import static br.org.eldorado.cst.collector.constants.Constants.DB_VERSION;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.org.eldorado.cst.collector.data.db.room.dao.CollectedDao;
import br.org.eldorado.cst.collector.data.db.room.dao.entities.SyncedData;
import br.org.eldorado.cst.collector.data.db.room.dao.entities.CollectedData;
import br.org.eldorado.cst.collector.data.db.room.dao.SyncedDao;

@Database(entities = {CollectedData.class, SyncedData.class}, version = DB_VERSION, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "CSTDatabase.db";
    private static AppDatabase INSTANCE;
    public abstract CollectedDao collectedDao();
    public abstract SyncedDao syncedDao();
    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                           AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
            }
        }
        return INSTANCE;
    }
}
