package com.example.josem.shoplistbesta;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class ListsTestActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ShopListItem shopItem;
    EditText itemQuantityEditText;
    EditText itemQuantityUnitsEditText;
    EditText itemNameEditText;
    ListView lv;

    ArrayList<String> itemsStringsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.shopItem = new ShopListItem();

        lv = (ListView) findViewById(R.id.listViewItems);

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        itemsStringsList = new ArrayList<String>();


        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                itemsStringsList);

        lv.setAdapter(arrayAdapter);

        itemQuantityEditText = (EditText) findViewById(R.id.editTextQuantity);
        itemQuantityUnitsEditText = (EditText) findViewById(R.id.editTextQuantityUnits);
        itemNameEditText = (EditText) findViewById(R.id.editTextItemName);

        itemQuantityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                shopItem.setQuantity(Float.parseFloat(charSequence.toString()));
                String c = charSequence.toString();
                if (c.length() == 0) shopItem.setQuantity(1);
                else shopItem.setQuantity(Float.parseFloat(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {
/*                String c = editable.toString();
                if(c.length() == 0) shopItem.setQuantity(1);
                else shopItem.setQuantity(Float.parseFloat(editable.toString()));*/
            }
        });

        itemQuantityUnitsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                shopItem.setQuantityUnits(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
//               shopItem.setQuantityUnits(editable.toString());
            }
        });

        itemNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                shopItem.setName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
//               shopItem.setName(editable.toString());
            }
        });

        Button addButton = (Button) findViewById(R.id.buttonAddItem);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                shopItem.setQuantity(itemQuantityEditText.toString());
                shopItem.setQuantityUnits(itemQuantityUnitsEditText.toString());
                shopItem.setName(itemNameEditText.toString());*/
                if (shopItem.stringReady()) itemsStringsList.add(shopItem.toString());
//                Toast.makeText(view.getContext(),shopItem.toString(),Toast.LENGTH_SHORT).show();


            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_request_test) {
            Intent intent = new Intent(this, RequestTest.class);
            startActivity(intent);
        } else if (id == R.id.nav_lispra_json_api_cookie_test) {
            Intent intent = new Intent(this, LoginTest.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

           /* Intent intent = new Intent(this, DisplayMessageActivity.class);
            EditText editText = (EditText) findViewById(R.id.edit_message);
            String message = editText.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);*/
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
