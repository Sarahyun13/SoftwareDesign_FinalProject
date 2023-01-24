package edu.csumb.separk.bookrentalsystem;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PlaceHoldActivity extends Activity implements View.OnClickListener {
    private EditText pickupEditText, returnEditText, placeHoldBookTitleEditText, placeHoldUserNameEditText, placeHoldPasswordEditText;
    private TextView placeHoldTextView1, placeHoldTextView2;
    private Button availableButton, confirmButton;
    private int pickupDate, returnDate;
    private String userName, password, bookTitle;
    private int count = 0;
    private int days = 0;

    private UserDatabase userDB;
    private BookDatabase bookDB;
    private TransDatabase transDB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_hold);

        pickupEditText = (EditText) findViewById(R.id.pickupEditText);
        returnEditText = (EditText) findViewById(R.id.returnEditText);
        placeHoldBookTitleEditText = (EditText) findViewById(R.id.placeHoldBookTitleEditText);
        placeHoldUserNameEditText = (EditText) findViewById(R.id.placeHoldUserNameEditText);
        placeHoldPasswordEditText = (EditText) findViewById(R.id.placeHoldPasswordEditText);
        placeHoldTextView1 = (TextView) findViewById(R.id.placeHoldTextView1);
        placeHoldTextView2 = (TextView) findViewById(R.id.placeHoldTextView2);
        availableButton = (Button) findViewById(R.id.availableButton);
        confirmButton = (Button) findViewById(R.id.confirmButton);

        availableButton.setOnClickListener(this);
        confirmButton.setOnClickListener(this);

        userDB = UserDatabase.getDatabase(this);
        bookDB = BookDatabase.getDatabase(this);
        transDB = TransDatabase.getDatabase(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.availableButton:
                pickupDate = Integer.parseInt(pickupEditText.getText().toString());
                returnDate = Integer.parseInt(returnEditText.getText().toString());
                days = returnDate - pickupDate + 1;

                if (days > 7) {
                    placeHoldTextView1.setText("Please enter less than 7 days for rental.");
                    Toast.makeText(this, "Please enter less than 7 days for rental.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    List<Book> bookList = bookDB.getBookDao().getAll();
                    List<String> availableBookList = new ArrayList<>();
                    List<String> unavailableBookList = new ArrayList<>();
                    boolean available = true;

                    for (Book book : bookList) {
                        char[] availability = book.getAvailability().toCharArray();
                        for (int i = pickupDate; i <= returnDate; i++) {
                            if (availability[i] == '-') {
                                available = false;
                                unavailableBookList.add(book.getTitle());
                                if (availableBookList.contains(book.getTitle())) {
                                    availableBookList.remove(book.getTitle());
                                }
                                break;
                            }
                        }
                        if (available) {
                            if (!unavailableBookList.contains(book.getTitle()) && !availableBookList.contains(book.getTitle())) {
                                availableBookList.add(book.getTitle());
                            }
                        }
                        available = true;
                    }

                    if (availableBookList.size() <= 0) {
                        placeHoldTextView1.setText("There is no book available for the dates entered.");
                        Toast.makeText(this, "There is no book available for the dates entered.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        String availableBookListText = "===== Available Book List =====";
                        for (String bookTitle : availableBookList) {
                            availableBookListText += "\n" + bookTitle;
                        }
                        Toast.makeText(this, availableBookListText, Toast.LENGTH_SHORT).show();
                        placeHoldTextView1.setText("Please enter the book title that you want to rent out.");
                    }
                }
                break;

            case R.id.confirmButton:
                bookTitle = placeHoldBookTitleEditText.getText().toString();
                userName = placeHoldUserNameEditText.getText().toString();
                password = placeHoldPasswordEditText.getText().toString();
                String pickupDateText = "Dec " + pickupDate + " 2022";
                String returnDateText = "Dec " + returnDate + " 2022";
                List<User> userList = userDB.getUserDao().getAll();
                List<Book> bookList = bookDB.getBookDao().getBooksByTitle(bookTitle);
                User matchUser = null;
                Book matchBook = null;

                for (User user : userList) {
                    if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                        matchUser = user;
                        break;
                    }
                }
                if (matchUser == null) {
                    count++;
                    if (count == 2) {
                        Toast.makeText(this, "Invalid information.", Toast.LENGTH_SHORT).show();
                        count = 0;
                        finish();
                    } else {
                        placeHoldTextView2.setText("Please reenter the user name and password.");
                    }
                } else {
                    placeHoldTextView2.setText("Confirmed.");
                    for (Book book : bookList) {
                        if (book.getTitle().equals(bookTitle)) {
                            matchBook = book;
                        }
                        break;
                    }
                    char[] availability = matchBook.getAvailability().toCharArray();
                    String availabilityStr;
                    for (int i = pickupDate; i <= returnDate; i++) {
                        availability[i] = '-';
                    }
                    availabilityStr = new String(availability);
                    Book newReservation = new Book(bookTitle, matchBook.getAuthor(), matchBook.getFee(), userName, pickupDate, returnDate, availabilityStr);
                    bookDB.getBookDao().insert(newReservation);

                    LocalDateTime dateTime = LocalDateTime.now();
                    String currentDateTime = dateTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"));
                    Trans trans = new Trans(userName, pickupDateText, returnDateText, matchBook.getTitle(), matchBook.getFee() * days, currentDateTime);
                    transDB.getTransDao().insert(trans);

                    String result = "Rent success.\n" + userName + ", " + pickupDateText + ", " + returnDateText + ", " + matchBook.getTitle() + ", $" + matchBook.getFee() * days;
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                    finish();
                }

                break;

        }
    }
}
