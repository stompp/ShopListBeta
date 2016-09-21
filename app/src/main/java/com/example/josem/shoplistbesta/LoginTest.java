package com.example.josem.shoplistbesta;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.riggitt.utils.wpjson.api.Request;
import com.riggitt.utils.wpjson.api.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginTest extends AppCompatActivity {

    private EditText userEditText;
    private EditText passwordEditText;
    private Button getCookieButton;
    private TextView outputTextView;
//    private Connector c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        this.userEditText = (EditText) findViewById(R.id.userEditText);
        this.passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        this.getCookieButton = (Button) findViewById(R.id.generateCookieButton);
        this.outputTextView = (TextView) findViewById(R.id.outputTextView);

        this.getCookieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                test();
                startAuthorization();
                showOutput("AUTH STARTED");

            }
        });
    }

    public void startAuthorization() {
        String user = this.userEditText.getText().toString();
        String password = this.passwordEditText.getText().toString();
        startAuthorization(user, password, new Request.OnRequestDoneListener() {
            @Override
            public void onRequestDone(Request request) {
//                StringBuilder sb = new StringBuilder();
//                sb.append("REQUEST DONE\r\n");
                if (request != null) {
                    Response r = request.getResponse();
                    if (r != null) {
                        showOutput(r.test());
//                        sb.append(String.format("Response Code : %d \r\n", r.getResponseCode()));
//                        sb.append("Response Content:\r\n");
//                        sb.append(r.getResponseContent());

                    }
                }

//                showOutput(sb.toString());
            }
        });
    }

    public void startAuthorization(String user, String password, Request.OnRequestDoneListener listener) {

        Request r = new Request();
        r.setPath("/api/user/generate_auth_cookie/");
        r.postParamsInBody(true);

        r.setRequestParameter("username", user);
        r.setRequestParameter("password", password);
        r.setRequestParameter("insecure", "cool");
        r.setRequestParameter("dev", "1");
        r.setOnRequestDoneListener(listener);
        r.start();
    }

    public void testRequest() {
        String user = this.userEditText.getText().toString();
        String password = this.passwordEditText.getText().toString();

        Request r = new Request();
        r.setPath("/api/user/generate_auth_cookie/");
        r.postParamsInBody(true);

        r.setRequestParameter("username", user);
        r.setRequestParameter("password", password);
        r.setRequestParameter("insecure", "cool");
        r.setRequestParameter("dev", "1");
        r.setOnRequestDoneListener(new Request.OnRequestDoneListener() {
            @Override
            public void onRequestDone(Request request) {
                StringBuilder sb = new StringBuilder();
                sb.append("REQUEST DONE\r\n");
                if (request != null) {
                    Response r = request.getResponse();
                    if (r != null) {
                        sb.append(String.format("Response Code : %d \r\n", r.getResponseCode()));
                        sb.append("Response Content:\r\n");
                        sb.append(r.getResponseContent());
                    }
                }

                showOutput(sb.toString());
            }
        });
//        showOutput(r.test());
        r.start();

//        showOutput("REQUEST STARTED, RESPONSE PENDING...");
    }


    public void showOutput(String s) {
        this.outputTextView.setMovementMethod(new ScrollingMovementMethod());
        this.outputTextView.setText(s);
        this.outputTextView.scrollTo(0,0);
    }
}
