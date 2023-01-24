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

public class CancelHoldActivity extends Activity implements View.OnClickListener {
    private EditText cancelHoldUserNameEditText, cancelHoldPasswordEditText, cancelHoldBookIdEditText;
    private TextView cancelHoldTextView1, cancelHoldTextView2;
    private Button cancelHoldSigninButton, cancelButton;
    private int pickupDate, returnDate, bookId;
    private String userName, password;
    private int count = 0;

    private UserDatabase userDB;
    private BookDatabase bookDB;
    private TransDatabase transDB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_hold);

        cancelHoldUserNameEditText = (EditText) findViewById(R.id.cancelHoldUserNameEditText);
        cancelHoldPasswordEditText = (EditText) findViewById(R.id.cancelHoldPasswordEditText);
        cancelHoldBookIdEditText = (EditText) findViewById(R.id.cancelHoldBookIdEditText);
        cancelHoldTextView1 = (TextView) findViewById(R.id.cancelHoldTextView1);
        cancelHoldTextView2 = (TextView) findViewById(R.id.cancelHoldTextView2);
        cancelHoldSigninButton = (Button) findViewById(R.id.cancelHoldSigninButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        cancelHoldSigninButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        userDB = UserDatabase.getDatabase(this);
        bookDB = BookDatabase.getDatabase(this);
        transDB = TransDatabase.getDatabase(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelHoldSigninButton:
                userName = cancelHoldUserNameEditText.getText().toString();
                password = cancelHoldPasswordEditText.getText().toString();
                List<User> userList = userDB.getUserDao().getAll();
                User matchUser = null;

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
                        cancelHoldTextView1.setText("Please reenter the user name and password.");
                    }
                } else {
                    List<Book> rentBookList = bookDB.getBookDao().getBooksByUserName(userName);
                    if (rentBookList.size() <= 0) {
                        cancelHoldTextView1.setText("There is no reservation.");
                        Toast.makeText(this, "There is no reservation", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        cancelHoldTextView1.setText("Please enter the book ID that you want to cancel.");
                        String rentBookListText = "========== Reservation ==========";
                        for (Book book : rentBookList) {
                            rentBookListText += "\n" + book.getId() + ", "
                                    + book.getTitle() + ", "
                                    + "Dec " + book.getPickupDate() + ", "
                                    + "Dec " + book.getReturnDate();
                        }
                        Toast.makeText(this, rentBookListText, Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.cancelButton:
                bookId = Integer.parseInt(cancelHoldBookIdEditText.getText().toString());
                List<Book> bookList = bookDB.getBookDao().searchById(bookId);
                for (Book book : bookList) {
                    Book deleteBook = book;
                    bookDB.getBookDao().delete(deleteBook);

                    LocalDateTime dateTime = LocalDateTime.now();
                    String currentDateTime = dateTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"));
                    String pickupDateText = "Dec " + book.getPickupDate() + " 2022";
                    String returnDateText = "Dec " + book.getReturnDate() + " 2022";
                    Trans trans = new Trans(userName, pickupDateText, returnDateText, book.getTitle(), currentDateTime);
                    transDB.getTransDao().insert(trans);

                    String result = "Cancellation success.";
                    cancelHoldTextView2.setText("Cancellation success.");
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                }
        }
    }
}
