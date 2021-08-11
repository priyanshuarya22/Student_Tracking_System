package com.example.studenttrackingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    EditText et1, et2;
    Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et1 = findViewById(R.id.login_et1);
        et2 = findViewById(R.id.login_et2);
        b1 = findViewById(R.id.login_b1);
        b2 = findViewById(R.id.login_b2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sid = et1.getText().toString();
                String password = et2.getText().toString();
                //creating object of LoginAsyncClass and passing the object of current activity, user id and password as parameter
                LoginAsyncClass lac = new LoginAsyncClass(LoginActivity.this, sid, password);
                //executing the async class to log the user in
                lac.execute();
            }
        });
        //setting the onclick listener for signup button
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating a new intent for signup activity
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                //starting the activity
                startActivity(intent);
            }
        });
    }
}