package edu.csumb.separk.bookrentalsystem;

import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

public class CreateAccountActivity extends Activity implements View.OnClickListener{
    private EditText userNameEditText;
    private EditText passwordEditText;
    private Button signupButton;
    private String userName = "";
    private String password = "";
    private int incorrectCount = 0;
    private int duplicateCount = 0;

    private UserDatabase userDB;
    private TransDatabase transDB;
    //private BookDatabase bookDB;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        userNameEditText = (EditText) findViewById(R.id.userNameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        signupButton = (Button) findViewById(R.id.signupButton);

        signupButton.setOnClickListener(this);

        userDB = UserDatabase.getDatabase(this);
        transDB = TransDatabase.getDatabase(this);
        //bookDB = BookDatabase.getDatabase(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClick(View v){
        if(v.getId() == R.id.signupButton){
            userName = userNameEditText.getText().toString();
            password = passwordEditText.getText().toString();
            List<User> userList = userDB.getUserDao().searchByUserName(userName);

            boolean nameMatch = Pattern.matches("^(?=.*[a-zA-Z]{3,})(?=.*[0-9])[a-zA-Z0-9]{4,10}$", userName);
            boolean passwordMatch = Pattern.matches("^(?=.*[a-zA-Z]{3,})(?=.*[0-9])[a-zA-Z0-9]{4,10}$", password);

            if(!nameMatch || !passwordMatch){
                incorrectCount++;
                if(incorrectCount == 2){
                    Toast.makeText(this, "Signup Fail(Incorrect format).", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(this, "Signup Fail(Incorrect format). Please enter again.", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                if(userName.equals("Admin2")){
                    Toast.makeText(this, "Signup Fail(Can't use Admin2)", Toast.LENGTH_SHORT).show();
                }
                else if(userList.size()<=0) {
                    User user = new User(userName, password);
                    userDB.getUserDao().insert(user);

                    LocalDateTime dateTime = LocalDateTime.now();
                    String currentDateTime = dateTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"));
                    Trans trans = new Trans(userName, currentDateTime);
                    transDB.getTransDao().insert(trans);
                    Toast.makeText(this, "Signup Success", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    duplicateCount++;
                    if(duplicateCount == 2) {
                        Toast.makeText(this, "Signup Fail(Already exist account)", Toast.LENGTH_SHORT).show();
                        duplicateCount = 0;
                        finish();
                    }
                    else{
                        Toast.makeText(this, "Signup Fail(Already exist account). Please enter again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
