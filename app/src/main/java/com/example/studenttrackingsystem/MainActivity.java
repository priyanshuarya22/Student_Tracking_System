package com.example.studenttrackingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getting the shared preferences
        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
        //getting the user id and password
        String sid = sh.getString("sid", "");
        String password = sh.getString("password", "");
        //checking if there are any passwords and id stored in the shared preference
        if(sid.equals("") && password.equals("")) {
            //if no launching the Login activity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else {
            //if yes launching the MainActivity
            Intent intent;
            if(sid.equals("admin")) {
                intent = new Intent(MainActivity.this, AdminMapsActivity.class);
            }
            else {
                intent = new Intent(MainActivity.this, StudentsMapsActivity.class);
            }
            startActivity(intent);
        }
    }
}