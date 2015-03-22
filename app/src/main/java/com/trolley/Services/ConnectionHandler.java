package com.trolley.Services;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sunny on 22/3/2015.
 */
public class ConnectionHandler implements Runnable {

    private HttpURLConnection getConnection(String targetURL, String urlParameters) throws IOException {
        URL url = new URL(targetURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setReadTimeout(30000 /* milliseconds */);
        connection.setConnectTimeout(45000 /* milliseconds */);

        connection.setRequestProperty("Content-Length", "" +
                Integer.toString(urlParameters.getBytes().length));
        connection.setRequestProperty("Content-Language", "en-US");
        connection.addRequestProperty("X-Parse-Application-Id", ApiKeys.APPLICATION_ID);
        connection.addRequestProperty("X-Parse-REST-API-Key", ApiKeys.REST_API_KEy);
        return connection;
    }

    public String executePost(String targetURL, String urlParameters) throws IOException {
        HttpURLConnection connection = null;
        //Create connection
        connection = this.getConnection(targetURL, urlParameters);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type",
                "application/json");

        //Send request

        DataOutputStream wr = new DataOutputStream(
                connection.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();


        //Get Response
        connection.connect();
        int responseCode = connection.getResponseCode();
        Log.d("Trolley", "The responseCode  is: " + responseCode);
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        return response.toString();

    }

    @Override
    public void run() {
    }
}
