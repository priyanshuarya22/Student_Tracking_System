package com.example.studenttrackingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {
    EditText et1, et2, et3, et4;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        et1 = findViewById(R.id.signup_et1);
        et2 = findViewById(R.id.signup_et2);
        et3 = findViewById(R.id.signup_et3);
        et4 = findViewById(R.id.signup_et4);
        b1 = findViewById(R.id.signup_b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sid = et1.getText().toString();
                String password = et2.getText().toString();
                String name = et3.getText().toString();
                String pno = et4.getText().toString();
                SignupAsyncClass sac = new SignupAsyncClass(SignupActivity.this, sid, password, name, pno);
                sac.execute();
            }
        });
    }
}