package com.example.josem.shoplistbesta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.riggitt.utils.Utils;
import com.riggitt.utils.wpjson.api.Constants;
import com.riggitt.utils.wpjson.api.UserSession;

import org.json.JSONException;

public class UserMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView outPutTV;
    private UserSession user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.user_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        outPutTV = (TextView)findViewById(R.id.outputTV);


        loadUserFromSharedPreferences();

        if(this.user!=null){
            View header=(View)navigationView.getHeaderView(0);
            TextView userTextView = (TextView)header.findViewById(R.id.userNameTextView);
            TextView userMailTextView = (TextView)header.findViewById(R.id.userMailTextView);

            userTextView.setText(user.getUser().getDisplayname());
            userMailTextView.setText(user.getUser().getEmail());
            showOutput(user.getUser().getDisplayname() + " " + user.getUser().getEmail());
        }else{
            logOut();
        }
    }

    public boolean loadUserFromSharedPreferences(){
        this.user = UserSession.loadUserFromSharedPreferences(getApplication());
        if(this.user != null) return true;
        return false;
    }


    public void logOut(){
        if(this.user!=null){
            this.user.logOutFromApp(getApplication());

        }
        Intent intent = new Intent(this, LispraLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void showOutput(String s){
            this.outPutTV.setText(s);
    }
//    private boolean waitingForBackToClose = false;
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            if(waitingForBackToClose){
//                finish();
//            }else{
//                waitingForBackToClose = true;
//                Utils.shortToast(
//                        getApplicationContext(),
//                        getResources().getString(R.string.click_back_again_to_close));
//
//                Handler h = new Handler();
//                h.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        waitingForBackToClose = false;
//
//                    }
//                },3000);
//
//            }
//
////            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.action_log_out){
           logOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_lispra_login) {
            Intent intent = new Intent(this, LispraLoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_request_test) {
            Intent intent = new Intent(this, RequestTest.class);
            startActivity(intent);
        } else if (id == R.id.nav_lists_test) {
            Intent intent = new Intent(this, ListsTestActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
