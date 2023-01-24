package edu.csumb.separk.bookrentalsystem;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User... users);

    @Update
    void update(User user);

    @Query("SELECT * FROM User WHERE userName = :userName")
    List<User> searchByUserName(String userName);

    @Query("SELECT * FROM User")
    List<User> getAll();
}
