package com.example.riddlemethis;

import android.os.Bundle;

import android.util.Log;
import androidx.navigation.NavController;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.Menu;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String TAG = "MainActivity";
    public static final String BACKENDLESS_TAG = "Backendless";

    private View navHostFragment;

    private SharedViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Backendless.initApp(this, "88F61704-EB84-2DDB-FFA9-9D248EEC5000",
                "2C84CAE4-48E0-0D8D-FFC3-C79C21CB3600");
        model = ViewModelProviders.of(this).get(SharedViewModel.class);
        navHostFragment = findViewById(R.id.fragment_mainactivity_navview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //NavController and id of navAction to navigate to
        NavController navController = Navigation.findNavController(navHostFragment);
        int navAction = -1;
        switch (id) {
            case R.id.menuitem_nav_discover:
                if(model.getCurrentFragment().isAnnotationPresent(LoginSection.class)){
                    navAction = R.id.action_loginGraph_to_discoverFragment;
                } else {
                    navAction = R.id.action_global_discoverFragment2;
                }
                break;
            case R.id.menuitem_nav_login:
                if(Backendless.UserService.CurrentUser() == null) {
                    navAction = R.id.action_global_loginGraph;
                } else {
                    if(model.getCurrentFragment().isAnnotationPresent(LoginSection.class)){
                        navAction = R.id.action_loginGraph_to_myAccountFragment;
                    } else {
                        navAction = R.id.action_global_myAccountFragment;
                    }
                }
                break;
            case R.id.menuitem_nav_ownriddles:
                if(model.getCurrentFragment().isAnnotationPresent(LoginSection.class)){
                    navAction = R.id.action_loginGraph_myRiddlesGraph;
                } else {
                    navAction = R.id.action_global_myRiddlesGraph;
                }
                break;
            case R.id.menuitem_nav_saved:
                if(model.getCurrentFragment().isAnnotationPresent(LoginSection.class)) {
                    navAction = R.id.action_loginGraph_to_savedRiddlesFragment;
                } else {
                    navAction = R.id.action_global_savedRiddlesFragment;
                }
                break;
        }

        //navigate here
        if (navAction != -1) {
            navController.navigate(navAction);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Backendless.UserService.logout(new AsyncCallback<Void>() {
            @Override
            public void handleResponse(Void response) {
                Log.d(TAG, "onStop: Backendless user logged out.");
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.d(TAG, "onStop: Backendless user logout failed.");
            }
        });
    }
}
