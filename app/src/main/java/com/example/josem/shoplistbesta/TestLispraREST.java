package com.example.josem.shoplistbesta;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.riggitt.utils.User;
import com.riggitt.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class TestLispraREST extends AppCompatActivity {

    private String default_url = "http://wplispra.riggitt.org/prueba.php";
    private User user;

    private EditText urlEditText;
    private TextView outputTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_lispra_rest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        urlEditText = (EditText)findViewById(R.id.lispraTestRestUrlInput);
        urlEditText.setText(default_url);

        outputTextView = (TextView)findViewById(R.id.lispraTestRestOutputTextView);
        outputTextView.setText("Push the button");

        user = new User("laviejadelgaita@gmail.com","admin");

    }

    public void testButtonPerform (View v ){


        // encode
//        String ps = "lavieja:admin";
//        Authorization
//        String tmp = "Basic " + Base64.encodeToString(ps.getBytes(),Base64.DEFAULT);
//        Snackbar.make(v,tmp,Snackbar.LENGTH_SHORT).show();
//        Snackbar.make(v,user.getBasicBase64UserPasswordEncodedString(),Snackbar.LENGTH_SHORT).show();
//        String url = urlEditText.getText().toString();
//        outputTextView.setText(url);
//        Toast.makeText(this,url,Toast.LENGTH_SHORT).show();
        new TestRest().execute(urlEditText.getText().toString());

    }


    private class TestRest extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {

          try {
              return downloadUrl(strings[0]);
          }catch (Exception e){

              return "Error";
          }


        }


        protected void onPreExecute() {
//            Toast.makeText(getApplicationContext(),"On pre",Toast.LENGTH_SHORT).show();
        }


        protected void onPostExecute(String s) {
//            Toast.makeText(getApplicationContext(),"On post : " + s,Toast.LENGTH_SHORT).show();

            outputTextView.setMovementMethod(new ScrollingMovementMethod());
            outputTextView.setText(s);
//            outputTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
//            super.onPostExecute(s);
        }

        private String downloadUrl(String myurl) throws IOException {
            InputStream is = null;

            try {

                URL url = new URL(myurl);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
//                Log.d("Debug", "basbdbasdbsabdsa: " + user.getBasicBase64UserPasswordEncodedString());

//                c.setRequestProperty("Authorization","akakak");
//                c.setReadTimeout(10000 /* milliseconds */);
//                c.setConnectTimeout(15000 /* milliseconds */);
                c.setRequestMethod("POST");
                c.setRequestProperty("Auth",user.getBase64UserPasswordEncodedString());
//                c.setDoInput(true);
                // Starts the query
                c.connect();
                int response = c.getResponseCode();
//                Log.d("Debug", "The response is: " + response);
                is = c.getInputStream();

                // Convert the InputStream into a string
//                String contentAsString = readIt(is, len);
//                return contentAsString;
                String responseContent = Utils.toString(is);
//                String responseContent = getResponseContent(is);
                return responseContent;


                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }

        private String getResponseContent(InputStream stream) throws IOException {
            BufferedReader r = new BufferedReader(new InputStreamReader(stream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
            return total.toString();
        }


        // Reads an InputStream and converts it to a String.
//        private String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
//            Reader reader = null;
//            reader = new InputStreamReader(stream, "UTF-8");
//            char[] buffer = new char[len];
//            reader.read(buffer);
//
//            return new String(buffer);
//        }

//        @Override
//        protected void onProgressUpdate(String... values) {
//            super.onProgressUpdate(values);
//        }

//        @Override
//        protected void onCancelled(String s) {
//            super.onCancelled(s);
//        }
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//        }
    }









}
