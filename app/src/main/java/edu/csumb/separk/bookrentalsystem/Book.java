package edu.csumb.separk.bookrentalsystem;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Array;
import java.util.ArrayList;


@Entity(tableName = "Book")
public class Book {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String author;
    private double fee;
    private String userName;
    private String availability;
    private int pickupDate, returnDate;

    public Book() {
        // default constructor - no initialization.
    }

    public Book(String title, String author, double fee) {
        this.title = title;
        this.author = author;
        this.fee = fee;
        this.userName = "";
        this.availability = "-++++++++++++++++++++++++++++++++";
        this.pickupDate = 0;
        this.returnDate = 0;
    }

    public Book(String title, String author, double fee, String userName, int pickupDate, int returnDate, String availability){
        this.title = title;
        this.author = author;
        this.fee = fee;
        this.userName = userName;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
        this.availability = availability;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getFee() {
        return fee;
    }

    public String getUserName() {
        return userName;
    }

    public int getPickupDate() {
        return pickupDate;
    }

    public int getReturnDate() {
        return returnDate;
    }

    public String getAvailability() {
        return availability;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPickupDate(int pickupDate) {
        this.pickupDate = pickupDate;
    }

    public void setReturnDate(int returnDate) {
        this.returnDate = returnDate;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}

