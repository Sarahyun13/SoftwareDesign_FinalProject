package edu.csumb.separk.bookrentalsystem;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransDao {
    @Insert
    void insert(Trans... trans);

    @Update
    void update(Trans trans);

    @Delete
    void delete(Trans trans);

    @Query("SELECT * FROM Trans WHERE title = :title")
    List<Trans> searchByTitle(String title);

    @Query("SELECT * FROM Trans")
    List<Trans> getAll();
}
