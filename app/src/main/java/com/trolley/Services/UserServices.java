package com.trolley.Services;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.trolley.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by sunny on 22/3/2015.
 */
public class UserServices {
    private final String ADD_USER_URL = "https://api.parse.com/1/classes/User";
    Activity activity;
    public boolean addUserAccount(User user,Activity activity) {
        this.activity=activity;
        //            object.put("emailVerified", user.isEmailVerified());
        ConnectivityManager connMgr = (ConnectivityManager)
                this.activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            ParseUser userOBJ = new ParseUser();
            userOBJ.setUsername(user.getEmail());
            userOBJ.setPassword( user.getPassword());
            userOBJ.setEmail( user.getEmail());
            userOBJ.put("name", user.getName());
            userOBJ.put("phone", user.getPhone());
            userOBJ.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException exception) {
                    if(exception==null){
                        Toast.makeText(UserServices.this.activity, "SuccessFul", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(UserServices.this.activity, "UnSuccessFul", Toast.LENGTH_LONG).show();
                    }
                }

            });
        } else {
            Toast.makeText(UserServices.this.activity, "No Network connection available", Toast.LENGTH_LONG).show();
        }

        return true;
    }
   /* // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.


    private class AsyncUserAccountCreator extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                ConnectionHandler handler = new ConnectionHandler();
                String response=handler.executePost(urls[0], urls[1]);
            } catch (IOException e) {
                return "Registration Unsuccessful";
            }
            return "Registration successful";
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(UserServices.this.activity, result, Toast.LENGTH_LONG).show();
        }
    }*/

}
