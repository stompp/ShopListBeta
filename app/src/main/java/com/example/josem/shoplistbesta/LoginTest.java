package com.example.josem.shoplistbesta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.riggitt.utils.Utils;
import com.riggitt.utils.wpjson.api.Constants;
import com.riggitt.utils.wpjson.api.Request;
import com.riggitt.utils.wpjson.api.Response;
import com.riggitt.utils.wpjson.api.UserController;
import com.riggitt.utils.wpjson.api.UserSession;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginTest extends AppCompatActivity {
//    public final static String LOGGED_USER = "riggitt.utils.wpjson.api.LoggedUser";

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

    public void saveToSharedPreferences(UserSession us) {
        SharedPreferences sp = getApplication().getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString("TEST", "SOY UN TEST");
        e.putString(Constants.USER_SESSION_INTENT_EXTRA, us.toString());
        e.apply();

//        sp.edit().putString(Constants.USER_SESSION_INTENT_EXTRA,us.toString()).commit();
    }

    public void logToUserMain(UserSession us) {

        Intent intent = new Intent(this, UserMainActivity.class);
        intent.putExtra(Constants.USER_SESSION_INTENT_EXTRA, us.toString());
        startActivity(intent);

    }

    public void startAuthorization() {
        String user = this.userEditText.getText().toString();
        String password = this.passwordEditText.getText().toString();

        UserController.startAuthorization(getApplication(),user, password, true, new UserController.OnAuthorizationDoneListener() {
            @Override
            public void onAuthorizationSuccess(UserSession user) {
                Toast.makeText(getApplicationContext(), "AUTH SUCCES", Toast.LENGTH_SHORT).show();
                saveToSharedPreferences(user);
                logToUserMain(user);
//                showOutput(user.toString());
                showOutput("LOGGING IN!");

            }

            @Override
            public void onAuthorizationFail(Response r) {
                Toast.makeText(getApplicationContext(), "AUTH FAILED", Toast.LENGTH_SHORT).show();
                showOutput(r.test());
            }
        });
//        UserController.startAuthorization(user, password, true,new Request.OnRequestDoneListener() {
//            @Override
//            public void onRequestDone(Request request) {
//                if (request != null) {
//                    Response r = request.getResponse();
//                    if (r != null) {
//                        try {
//                            ResponseContentReader cp = r.getContent();
//                            showOutput(cp.debugKeys());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
////                        showOutput(r.test());
////                        parseResponseContent(r.getResponseContent());
////                        sb.append(String.format("Response Code : %d \r\n", r.getResponseCode()));
////                        sb.append("Response Content:\r\n");
////                        sb.append(r.getResponseContent());
//
//                    }
//                }
//
////                showOutput(sb.toString());
//            }
//        });
    }


//    public void startCookieValidation(String cookie) {
//
//        UserController.startCookieValidation(cookie, true, new UserController.OnAuthorizationDoneListener() {
//            @Override
//            public void onAuthorizationSuccess(UserSession user) {
//                Utils.shortToast(getApplicationContext(), "COOKIE VALIDATED");
//            }
//
//            @Override
//            public void onAuthorizationFail(Response r) {
//                Utils.shortToast(getApplicationContext(), "COOKIE VALIDATION FAILED");
//            }
//        });
//    }

    public void parseResponseContent(String content) {
        try {
            JSONObject o = new JSONObject(content);

            String status = o.optString("status");

            Toast.makeText(getApplicationContext(), "Status is " + status, Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        this.outputTextView.scrollTo(0, 0);
    }



}
