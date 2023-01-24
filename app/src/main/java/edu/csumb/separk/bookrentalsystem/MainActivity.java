package edu.csumb.separk.bookrentalsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity implements OnClickListener {
    private Button createAccountButton, placeHoldButton, cancelHoldButton, manageSystemButton;
    private UserDatabase userDB;
    private TransDatabase transDB;
    private BookDatabase bookDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAccountButton = (Button)findViewById(R.id.createAccountButton);
        placeHoldButton = (Button)findViewById(R.id.placeHoldButton);
        cancelHoldButton = (Button)findViewById(R.id.cancelHoldButton);
        manageSystemButton = (Button)findViewById(R.id.manageSystemButton);

        createAccountButton.setOnClickListener(this);
        placeHoldButton.setOnClickListener(this);
        cancelHoldButton.setOnClickListener(this);
        manageSystemButton.setOnClickListener(this);

        userDB = UserDatabase.getDatabase(this);
        transDB = TransDatabase.getDatabase(this);
        bookDB = BookDatabase.getDatabase(this);

        List<User> userList = userDB.getUserDao().getAll();
        if(userList.size() <= 0){
            User[] defaultUser = new User[3];
            defaultUser[0] = new User("alice5", "csumb100");
            defaultUser[1] = new User("Brian7", "123abc");
            defaultUser[2] = new User("chris12", "CHRIS12");

            userDB.getUserDao().insert(defaultUser);
        }

        List<Book> bookList = bookDB.getBookDao().getAll();
        if(bookList.size() <= 0){
            Book[] defaultBook = new Book[3];
            defaultBook[0] = new Book("Hot Java", "S. Narayanan", 1.50);
            defaultBook[1] = new Book("Fun Java", "Y. Byun", 2.00);
            defaultBook[2] = new Book("Algorithm for Java", "K. Alice", 2.25);

            bookDB.getBookDao().insert(defaultBook);
        }

        String userBookListText = "========== User List ==========";
        for(User user:userList) {
            userBookListText += "\n" + user.getId() + ", " + user.getUserName();
        }

        userBookListText += "\n========== Book List ==========";
        for(Book book:bookList){
            userBookListText += "\n" + book.getId() + ", " + book.getTitle();
        }

        Toast.makeText(this, userBookListText, Toast.LENGTH_SHORT).show();
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.createAccountButton:
                Intent createAccount = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivity(createAccount);
                break;

            case R.id.placeHoldButton:
                Intent placeHold = new Intent(getApplicationContext(), PlaceHoldActivity.class);
                startActivity(placeHold);
                break;

            case R.id.cancelHoldButton:
                Intent cancelHold = new Intent(getApplicationContext(), CancelHoldActivity.class);
                startActivity(cancelHold);
                break;

            case R.id.manageSystemButton:
                Intent manageSystem = new Intent(getApplicationContext(), ManageSystemActivity.class);
                startActivity(manageSystem);
                break;
        }
    }
}