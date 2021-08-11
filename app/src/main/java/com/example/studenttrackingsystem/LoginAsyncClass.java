package com.example.studenttrackingsystem;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

public class LoginAsyncClass extends AsyncTask<Void, Void, Void> {
    //creating variables
    String sid, password;
    @SuppressLint("StaticFieldLeak")
    LoginActivity loginActivity;
    LoginUser loginUser;
    ProgressDialog pd;
    String result;

    //constructor of this class taking object of login class, user id and password as arguments
    LoginAsyncClass(LoginActivity loginActivity, String sid, String password) {
        //assigning the value of arguments to local variables
        this.loginActivity = loginActivity;
        this.sid = sid;
        this.password = password;
        //creating objects
        loginUser = new LoginUser();
        pd = new ProgressDialog(loginActivity);
        //setting message on progress dialog
        pd.setMessage("Logging in...");
        //starting the progress dialog
        pd.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if(sid.equals("admin")) {
            if(!password.equals("007")) {
                Toast.makeText(loginActivity, "Admin Password Incorrect", Toast.LENGTH_SHORT).show();
            }
            else {
                result = "Passed";
            }
        }
        else {
            result = loginUser.checkUser(sid, password);
        }
        //checking if the user exists
        if (result.equals("Passed")) {
            //getting the shared preferences
            SharedPreferences sharedPreferences = loginActivity.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
            //creating editor for shared preferences
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            //putting userid, password and user name in shared preferences
            myEdit.putString("sid", sid);
            myEdit.putString("password", password);
            //applying the edits
            myEdit.apply();
            //creating a new intent for MainActivity
            Intent intent = new Intent(loginActivity, MainActivity.class);
            //starting the activity
            loginActivity.startActivity(intent);
        }
        else {
            //creating a toast to inform user in case of a error
            Toast.makeText(loginActivity, result, Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        //stopping the progress dialog
        pd.dismiss();
    }
}
