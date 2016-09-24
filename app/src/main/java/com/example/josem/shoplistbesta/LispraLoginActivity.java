package com.example.josem.shoplistbesta;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.riggitt.utils.Utils;
import com.riggitt.utils.wpjson.api.Constants;
import com.riggitt.utils.wpjson.api.Response;
import com.riggitt.utils.wpjson.api.UserController;
import com.riggitt.utils.wpjson.api.UserSession;

public class LispraLoginActivity extends AppCompatActivity {

    TextInputLayout usernameWrapper;
    TextInputLayout passwordWrapper;

    LinearLayout inputs;
    TextView statusOutput;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lispra_login);

        usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);

        usernameWrapper.setHint("User or Email");
        passwordWrapper.setHint("Password");

        inputs = (LinearLayout) findViewById(R.id.inputsLayout);
        statusOutput = (TextView) findViewById(R.id.statusOutput);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Button login = (Button) findViewById(R.id.login_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                startAuthorization();

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        startValidation();
    }

//    private boolean waitingForBackToClose = false;

//    @Override
//    public void onBackPressed() {
//
//        if (waitingForBackToClose) {
//            finish();
//        } else {
//            waitingForBackToClose = true;
//            Utils.shortToast(
//                    getApplicationContext(),
//                    getResources().getString(R.string.click_back_again_to_close));
//
//            Handler h = new Handler();
//            h.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    waitingForBackToClose = false;
//
//                }
//            }, 3000);
//
//        }
//
//    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void testProgress() {
        progressStart("Doing Something");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressReset();
            }

        }, 5000); // 5000ms delay
    }

    public boolean validateUser() {
        String user = usernameWrapper.getEditText().getText().toString();
        if (user.isEmpty()) {
            usernameWrapper.setError("User is empty");
            return false;
        }
        return true;
    }

    public boolean validatePassword() {
        String password = passwordWrapper.getEditText().getText().toString();
        if (password.isEmpty()) {
            passwordWrapper.setError("Password is empty");
            return false;
        }
        return true;
    }

    public boolean validateInputs() {
        return (validateUser() && validatePassword());
    }

    public void progressStart(String status) {
        inputs.setVisibility(View.GONE);
        statusOutput.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        statusOutput.setText(status);
        progressBar.setIndeterminate(true);
    }

    public void setProgressStatus(String status) {
        statusOutput.setText(status);
    }

    public void progressReset() {

        progressBar.setIndeterminate(false);
        statusOutput.setText("");
        progressBar.setVisibility(View.GONE);
        statusOutput.setVisibility(View.GONE);
        inputs.setVisibility(View.VISIBLE);
    }

    public void startUserMain() {

        Intent intent = new Intent(this, UserMainActivity.class);
        startActivity(intent);
    }

    public void logToUserMain(UserSession us) {
        us.logInToApp(getApplication());
        startUserMain();
    }

    private void startAuthorization() {
        progressStart("Validating Input");
        if (!validateInputs()) {
            progressReset();
            return;
        }

        String user = usernameWrapper.getEditText().getText().toString();
        String password = passwordWrapper.getEditText().getText().toString();

        setProgressStatus("Starting Authorization");

        UserController.startAuthorization(
                getApplication(),
                user,
                password,
                true,
                new UserController.OnAuthorizationDoneListener() {
                    @Override
                    public void onAuthorizationSuccess(UserSession user) {
                        Utils.shortToast(getApplicationContext(), "AUTH SUCCES");
//                        setProgressStatus("Authorization complete");
//                        startUserMain();
                        startValidation();
                    }

                    @Override
                    public void onAuthorizationFail(Response r) {
                        Utils.shortToast(getApplicationContext(), "AUTH FAILED");
                        progressReset();

                    }
                }
        );

    }


    public void startValidation() {

        UserController.validateUserSession(
                getApplication(),
                true,
                new UserController.UserSessionValidatorListener() {
                    @Override
                    public void onValidationStarted() {
                        progressStart("Validating user");
                    }

                    @Override
                    public void onValidationSuccess(UserSession user) {
                        Utils.shortToast(getApplicationContext(), "Validation success!!");
                        setProgressStatus("User validated");
                        startUserMain();
                    }

                    @Override
                    public void OnValidationFail(Response r) {
                        Utils.shortToast(getApplicationContext(), "Validation failed");
                        Utils.longToast(getApplicationContext(), r.getResponseContent());
                        progressReset();
                    }
                });


    }


}
