package edu.csumb.separk.bookrentalsystem;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;


@Entity(tableName = "User")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userName;
    private String password;
    //private List<Book> rentBookList = new ArrayList<>();//필요없음

    public User() {
        // default constructor - no initialization.
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    /*public List<Book> getRentBookList() {
        return rentBookList;
    }*/

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*public void setRentBookList(Book book) {
        rentBookList.add(book);
    }

    public void deleteBook(Book book) {
        rentBookList.remove(book);
    }*/
}