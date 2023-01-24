package edu.csumb.separk.bookrentalsystem;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Trans")
public class Trans {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String type;
    private String userName;
    private String title;
    private String pickupDate;
    private String returnDate;
    private double totalAmount;
    private String currentDateTime;

    public Trans ()
    {
        // default constructor - no initialization.
    }

    public Trans (String userName, String currentDateTime)
    {
        this.type = "New account";
        this.userName = userName;
        this.currentDateTime = currentDateTime;
        this.title = "";
        this.pickupDate = "";
        this.returnDate = "";
        this.totalAmount = 0;
    }

    public Trans(String userName, String pickupDate, String returnDate, String title, double totalAmount, String currentDateTime){
        this.type = "Place hold";
        this.userName = userName;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
        this.title = title;
        this.totalAmount = totalAmount;
        this.currentDateTime = currentDateTime;
    }

    public Trans(String userName, String pickupDate, String returnDate, String title, String currentDateTime){
        this.type = "Cancel hold";
        this.userName = userName;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
        this.title = title;
        this.totalAmount = 0;
        this.currentDateTime = currentDateTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUserName(String userName){ this.userName = userName;}

    public void setTitle(String title){this.title = title;};

    public void setPickupDate(String pickupDate){this.pickupDate = pickupDate;};

    public void setReturnDate(String returnDate){this.returnDate = returnDate;};

    public void setTotalAmount(double totalAmount){this.totalAmount = totalAmount;};

    public void setCurrentDateTime(String currentDateTime){this.currentDateTime = currentDateTime;};

    public int getId(){
        return id;
    }

    public String getType(){
        return type;
    }

    public String getUserName(){
        return userName;
    }

    public String getCurrentDateTime(){
        return currentDateTime;
    }

    public String getPickupDate(){
        return pickupDate;
    }

    public String getReturnDate(){
        return returnDate;
    }

    public String getTitle(){
        return title;
    }

    public double getTotalAmount(){
        return totalAmount;
    }
}