package edu.csumb.separk.bookrentalsystem;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Book.class}, version = 1)
public abstract class BookDatabase extends RoomDatabase {
    public abstract BookDao getBookDao();

    private volatile static BookDatabase dbInstance;

    static BookDatabase getDatabase(Context context) {
        if(dbInstance == null) {
            synchronized (Book.class) {
                if(dbInstance == null) {
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(),
                            BookDatabase.class,
                            "Book.DB").allowMainThreadQueries().build();
                }
            }
        }
        return dbInstance;
    }
}