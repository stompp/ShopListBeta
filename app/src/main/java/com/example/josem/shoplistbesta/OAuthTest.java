package com.example.josem.shoplistbesta;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.riggitt.utils.Connector;
import com.riggitt.utils.ConnectorConnection;
import com.riggitt.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OAuthTest extends AppCompatActivity {

    private EditText urlEditText;
    private EditText scriptEditText;
    private EditText userEditText;
    private EditText passwordEditText;
    private TextView outputTextView;
    private Spinner methodSpinner;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        urlEditText = (EditText) findViewById(R.id.urlEditText);
        scriptEditText = (EditText) findViewById(R.id.scriptEditText);
        userEditText = (EditText) findViewById(R.id.userEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        outputTextView = (TextView) findViewById(R.id.outputTextView);
        methodSpinner = (Spinner) findViewById(R.id.methodSpinner);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
                Connector c = getConnector();
                c.setConnectorListener(new Connector.ConnectorListener() {
                    @Override
                    public void onConnectionSuccess(Connector connector) {
                        Toast.makeText(getApplicationContext(), "Connection Success!!", Toast.LENGTH_SHORT).show();
                        outputTextView.setMovementMethod(new ScrollingMovementMethod());
                        if(connector.getConnectionResult().getResponseContent() == null) outputTextView.setText("Null content");
                        else outputTextView.setText(connector.getConnectionResult().getResponseContent());
                    }

                    @Override
                    public void onConnectionError(Connector connector) {
                        Toast.makeText(getApplicationContext(), "Connection Error!!", Toast.LENGTH_SHORT).show();
                        outputTextView.setText("CONNECTION ERROR");
                    }
                });

//                c.doListener();

                new ConnectorConnection().execute(c);

            }
        });


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


    }

    public Connector getConnector() {
        String url_str = urlEditText.getText().toString();
        String script_str = scriptEditText.getText().toString();
        String user_str = userEditText.getText().toString();
        String password_str = passwordEditText.getText().toString();
        String method = methodSpinner.getSelectedItem().toString();
        Connector c = new Connector(url_str, script_str, user_str, password_str, method);
//        c.setHttpMethod(method);
        return c;
    }



}
