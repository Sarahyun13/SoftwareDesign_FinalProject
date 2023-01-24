package edu.csumb.separk.bookrentalsystem;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Trans.class}, version = 1)
public abstract class TransDatabase extends RoomDatabase {
    public abstract TransDao getTransDao();

    private volatile static TransDatabase dbInstance;

    static TransDatabase getDatabase(Context context) {
        if(dbInstance == null) {
            synchronized (Trans.class) {
                if (dbInstance == null) {
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(),
                            TransDatabase.class,
                            "Trans.DB").allowMainThreadQueries().build();
                }
            }
        }
        return dbInstance;
    }
}