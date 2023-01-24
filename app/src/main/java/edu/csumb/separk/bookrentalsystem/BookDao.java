package edu.csumb.separk.bookrentalsystem;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao {
    @Insert
    void insert(Book... books);

    @Update
    void update(Book book);

    @Delete
    void delete(Book book);

    @Query("SELECT * FROM Book WHERE id = :id")
    List<Book> searchById(int id);

    @Query("SELECT * FROM Book WHERE userName = :userName")
    List<Book> getBooksByUserName(String userName);

    @Query("SELECT * FROM Book WHERE title = :title")
    List<Book> getBooksByTitle(String title);

    @Query("SELECT * FROM Book")
    List<Book> getAll();
}
