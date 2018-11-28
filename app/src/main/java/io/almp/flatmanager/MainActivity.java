package io.almp.flatmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private LinearLayout chatLayout;
    private LinearLayout dutiesLayout;
    private LinearLayout rentLayout;
    private LinearLayout shoppingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        Log.e("Uid", " " + getSharedPreferences("_", MODE_PRIVATE).getString("user_token", "empty")); // TODO: debugging only
        Log.e("Uid", " " + getSharedPreferences("_", MODE_PRIVATE).getLong("user_id", 0L));
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //    setSupportActionBar(toolbar);
        //  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //   ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        //           this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //   drawer.addDrawerListener(toggle);
        //<   toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        chatLayout = findViewById(R.id.chat);
        dutiesLayout = findViewById(R.id.duties);
        rentLayout = findViewById(R.id.rent);
        shoppingLayout = findViewById(R.id.shopping);

        chatLayout.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChatActivity.class);
            startActivity(intent);
        });
        dutiesLayout.setOnClickListener(v -> {
            Intent intent = new Intent(this, DutiesActivity.class);
            startActivity(intent);
        });

        rentLayout.setOnClickListener(v -> {
            Intent intent = new Intent(this, RentActivity.class);
            startActivity(intent);
        });
        shoppingLayout.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShoppingActivity.class);
            startActivity(intent);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

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
