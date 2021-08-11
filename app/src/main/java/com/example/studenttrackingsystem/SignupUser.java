package com.example.studenttrackingsystem;

import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SignupUser {

    public String insertStudent(String sid, String password, String name, String pno) {
        //setting server url
        final String SERVER_URL = ServerURL.serverURL + "signup.jsp";
        //setting character set to english
        final String CHAR_SET = "UTF-8"; // Encoding used for the parameters
        //setting output to null
        OutputStream output = null;
        //initialising response string to empty string
        String response = "";
        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //encoding data to be sent on server
            String data = URLEncoder.encode("sid", CHAR_SET) + "=" + URLEncoder.encode(sid, CHAR_SET);
            data += "&" + URLEncoder.encode("password", CHAR_SET) + "=" + URLEncoder.encode(password, CHAR_SET);
            data += "&" + URLEncoder.encode("name", CHAR_SET) + "=" + URLEncoder.encode(name, CHAR_SET);
            data += "&" + URLEncoder.encode("pno", CHAR_SET) + "=" + URLEncoder.encode(pno, CHAR_SET);
            // Create a connection to server using URL
            URLConnection connection = new URL(SERVER_URL).openConnection();
            //setting connection output to true
            connection.setDoOutput(true);
            //setting request property to predefined character set
            connection.setRequestProperty("Accept-Charset", CHAR_SET);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + CHAR_SET);
            //connecting output to output stream
            output = connection.getOutputStream();
            //writing data to output
            output.write(data.getBytes(CHAR_SET));
            //flushing the data
            output.flush();
            //closing output connection
            output.close();
            //creating a input stream
            InputStream responseStream = connection.getInputStream();
            //connecting buffered reader to input streamer
            BufferedReader rd = new BufferedReader(new InputStreamReader(responseStream,CHAR_SET));
            //creating a null string
            String line = null;
            //looping while the buffered reader still have lines
            while ((line = rd.readLine()) != null)
            {
                //appending lines to response
                response += line;
            }
        }
        catch (Exception e)
        {
            //setting response to error
            response=e.getMessage();
        }
        //returning response
        return response;
    }
}
