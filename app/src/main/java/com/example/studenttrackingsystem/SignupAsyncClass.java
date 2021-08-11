package com.example.studenttrackingsystem;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

public class SignupAsyncClass extends AsyncTask<Void, Void, Void> {
    //creating variables
    @SuppressLint("StaticFieldLeak")
    SignupActivity signupActivity;
    String sid, password, name, pno;
    ProgressDialog pd;
    SignupUser signupUser;
    String result;

    //creating a constructor which takes object of Signup class and array of string data
    SignupAsyncClass(SignupActivity signupActivity, String sid, String password, String name, String pno) {
        //assigning the value of parameters to local variables
        this.signupActivity = signupActivity;
        this.sid = sid;
        this.password = password;
        this.name = name;
        this.pno = pno;
        //creating objects
        signupUser = new SignupUser();
        pd = new ProgressDialog(signupActivity);
        //setting message on the progress dialog
        pd.setMessage("Signing in...");
        //showing the progress dialog
        pd.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        result = signupUser.insertStudent(sid, password, name, pno);
        //getting shared preferences
        SharedPreferences sharedPreferences = signupActivity.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        //creating editor for shared preferences
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        //putting userid, password and name in shared preferences
        myEdit.putString("sid", sid);
        myEdit.putString("password", password);
        //applying the edits
        myEdit.apply();
        //creating a new intent for MAinActivity
        Intent intent = new Intent(signupActivity, MainActivity.class);
        //starting the activity
        signupActivity.startActivity(intent);
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        //stopping the progress dialog
        pd.dismiss();
        //making toast to inform user of the result
        Toast.makeText(signupActivity, result, Toast.LENGTH_SHORT).show();
    }
}
