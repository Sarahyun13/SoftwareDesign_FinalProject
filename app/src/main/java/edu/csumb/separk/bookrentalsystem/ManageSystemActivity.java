package edu.csumb.separk.bookrentalsystem;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ManageSystemActivity extends Activity implements View.OnClickListener {
    private EditText adminUserNameEditText, adminPasswordEditText, manageTitleEditText, manageAuthorEditText, manageFeeEditText;
    private TextView askTextView;
    private Button adminSigninButton, yesButton, noButton;
    private String userName = "";
    private String password = "";
    private String title = "";
    private String author = "";
    private double fee;
    private int count = 0;

    private BookDatabase bookDB;
    private TransDatabase transDB;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_system);

        adminUserNameEditText = (EditText) findViewById(R.id.adminUserNameEditText);
        adminPasswordEditText = (EditText)findViewById(R.id.adminPasswordEditText);
        adminSigninButton = (Button) findViewById(R.id.adminSigninButton);
        askTextView = (TextView) findViewById(R.id.askTextView);
        manageTitleEditText = (EditText) findViewById(R.id.manageTitleEditText);
        manageAuthorEditText = (EditText) findViewById(R.id.manageAuthorEditText);
        manageFeeEditText = (EditText) findViewById(R.id.manageFeeEditText);
        yesButton = (Button) findViewById(R.id.yesButton);
        noButton = (Button) findViewById(R.id.noButton);

        adminSigninButton.setOnClickListener(this);
        yesButton.setOnClickListener(this);
        noButton.setOnClickListener(this);

        bookDB = BookDatabase.getDatabase(this);
        transDB = TransDatabase.getDatabase(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.adminSigninButton:
                userName = adminUserNameEditText.getText().toString();
                password = adminPasswordEditText.getText().toString();

                if(userName.equals("Admin2") && password.equals("Admin2")){
                    List<Trans> transList = transDB.getTransDao().getAll();
                    String transListText = "=========== Transaction ==========";
                    for(Trans trans : transList){
                        if(trans.getType().equals("New account")){
                            transListText += "\n" + trans.getId() + ", "
                                    + trans.getType() + ", "
                                    + trans.getUserName() + ", "
                                    + trans.getCurrentDateTime();
                        }
                        else if(trans.getType().equals("Place hold")){
                            transListText += "\n" + trans.getId() + ", "
                                    + trans.getType() + ", "
                                    + trans.getUserName() + ", "
                                    + trans.getPickupDate() + ", "
                                    + trans.getReturnDate() + ", "
                                    + trans.getTitle() + ", "
                                    + "$" + trans.getTotalAmount() + ", "
                                    + trans.getCurrentDateTime();
                        }
                        else if(trans.getType().equals("Cancel hold")){
                            transListText += "\n" + trans.getId() + ", "
                                    + trans.getType() + ", "
                                    + trans.getUserName() + ", "
                                    + trans.getPickupDate() + ", "
                                    + trans.getReturnDate() + ", "
                                    + trans.getTitle() + ", "
                                    + trans.getCurrentDateTime();
                        }
                    }
                    Toast.makeText(this, transListText, Toast.LENGTH_SHORT).show();
                    askTextView.setText("Do you want to add new book?");
                }
                else{
                    count++;
                    if(count==2){
                        Toast.makeText(this, "Invalid information.", Toast.LENGTH_SHORT).show();
                        count = 0;
                        finish();
                    }
                    else {
                        askTextView.setText("Please reenter the user name and password.");
                    }
                }
                break;

            case R.id.yesButton:
                title = manageTitleEditText.getText().toString();
                author = manageAuthorEditText.getText().toString();
                if(!TextUtils.isEmpty(manageFeeEditText.getText().toString())) {
                    fee = Double.parseDouble(manageFeeEditText.getText().toString());
                }
                List<Book> bookList = bookDB.getBookDao().getAll();
                List<String> bookTitleList = new ArrayList<>();

                for(Book book:bookList){
                    bookTitleList.add(book.getTitle());
                }

                if(manageTitleEditText.length()==0 || manageAuthorEditText.length()==0 || TextUtils.isEmpty(manageFeeEditText.getText().toString())){
                    Toast.makeText(this, "A book information is not valid.", Toast.LENGTH_SHORT).show();
                }
                else if(bookTitleList.contains(title)){
                    Toast.makeText(this, "A book is already exist.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Book book = new Book(title, author, fee);
                    bookDB.getBookDao().insert(book);
                    Toast.makeText(this, "A book has been added.", Toast.LENGTH_SHORT).show();
                }
                finish();
                break;

            case R.id.noButton:
                finish();
                break;
        }
    }
}
