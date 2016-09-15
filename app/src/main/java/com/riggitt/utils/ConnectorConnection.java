package com.riggitt.utils;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by josem on 11/09/2016.
 */
public class ConnectorConnection extends AsyncTask<Connector, Connector, Connector> {

    @Override
    protected Connector doInBackground(Connector... connectors) {

        Connector c = connectors[0];
        try {
//            if(connectors[0]==null) return null;
//            return downloadUrl(connectors[0]);
            return downloadUrl(c);
        } catch (Exception e) {

            c.setConnectionResult(new ConnectorConnectionResult("BACKGROUND ERROR", 999));

        }
        return c;


    }

    protected void onPostExecute(Connector connector) {
//            Toast.makeText(getApplicationContext(),"On post : " + s,Toast.LENGTH_SHORT).show();
        if (connector != null) {
            connector.onConnectionSuccess();
        }
    }


    private Connector downloadUrl(Connector connector) throws IOException {
        if (connector == null) return null;
        if (!connector.isReadyToConnect()) return null;

        InputStream is = null;
        URL url = new URL(connector.getFullUrl());
        HttpURLConnection c = (HttpURLConnection) url.openConnection();
//        c.setRequestMethod(connector.getHttpMethod());
        c.setRequestProperty("Authorization", connector.getBasicBase64UserPasswordEncodedString());
//                Log.d("Debug", "basbdbasdbsabdsa: " + user.getBasicBase64UserPasswordEncodedString());
//                c.setRequestProperty("Authorization","akakak");
        c.setReadTimeout(10000 /* milliseconds */);
        c.setConnectTimeout(15000 /* milliseconds */);

//        URLConnection connection = new URL(url).openConnection();
//        connection.setDoOutput(true); // Triggers POST.
//        connection.setRequestProperty("Accept-Charset", charset);
//        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
//
//        try (OutputStream output = connection.getOutputStream()) {
//            output.write(query.getBytes(charset));
//        }
//        c.setDoInput(true);
        // Starts the query
//        c.connect();
        int responseCode = c.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) is = c.getInputStream();
        else is = c.getErrorStream();

        String responseContent = Utils.toString(is);
        connector.setConnectionResult(new ConnectorConnectionResult(responseContent, responseCode));
//        connector.setResponseContent(responseContent);

        if (is != null) {
            is.close();
        }
        c.disconnect();
        return connector;

    }


}

