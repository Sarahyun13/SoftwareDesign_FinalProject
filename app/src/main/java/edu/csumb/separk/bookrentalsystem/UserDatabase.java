package edu.csumb.separk.bookrentalsystem;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();

    private volatile static UserDatabase dbInstance;

    static UserDatabase getDatabase(Context context) {
        if(dbInstance == null) {
            synchronized(User.class) {
                if (dbInstance == null) {
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class,
                            "User.DB").allowMainThreadQueries().build();
                }
            }
        }
        return dbInstance;
    }
}