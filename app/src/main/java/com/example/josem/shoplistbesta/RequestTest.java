package com.example.josem.shoplistbesta;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.riggitt.utils.wpjson.api.Request;
import com.riggitt.utils.wpjson.api.Response;

public class RequestTest extends AppCompatActivity {

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
        setContentView(R.layout.activity_request_test);
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
                startRequest();
            }
        });

    }

    public void startRequest(){

        String url = this.urlEditText.getText().toString();
        String script = this.scriptEditText.getText().toString();
        String user = this.userEditText.getText().toString();
        String password = this.passwordEditText.getText().toString();
        String method = this.methodSpinner.getSelectedItem().toString();

        Request r = new Request();
        r.setUrl(url);
        r.setPath(script);
        if(method!="GET"){
            r.setPostMode();
        }
        r.setOnRequestDoneListener(new Request.OnRequestDoneListener() {
            @Override
            public void onRequestDone(Request request) {
                if(request!=null){
                    Response rs = request.getResponse();
                    if(rs != null){
                        showOutput(rs.test());
                    }
                }

            }
        });

        r.start();
    }
    public void showOutput(String s) {
        this.outputTextView.setMovementMethod(new ScrollingMovementMethod());
        this.outputTextView.setText(s);
        this.outputTextView.scrollTo(0,0);
    }



}
