package com.coopmart.urbanfarming.urbanfarming.Activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.coopmart.urbanfarming.urbanfarming.Fragment.CatalogFragment;
import com.coopmart.urbanfarming.urbanfarming.Fragment.HistoryFragment;
import com.coopmart.urbanfarming.urbanfarming.Fragment.MainMenuFragment;
import com.coopmart.urbanfarming.urbanfarming.Fragment.PickupFragment;
import com.coopmart.urbanfarming.urbanfarming.Method.MagicBox;
import com.coopmart.urbanfarming.urbanfarming.Method.Navigator;
import com.coopmart.urbanfarming.urbanfarming.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private class NavBarListener implements NavigationView.OnNavigationItemSelectedListener {

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.h_main_menu) {
                changeHomeActivityDisplayedFragment(new MainMenuFragment(), false);
            } else if (id == R.id.h_pick_up) {
                changeHomeActivityDisplayedFragment(new PickupFragment(), false);
            } else if (id == R.id.h_shop) {
                changeHomeActivityDisplayedFragment(new HistoryFragment(), false);
            } else if (id == R.id.h_catalog) {
                changeHomeActivityDisplayedFragment(new CatalogFragment(), false);
            } else if (id == R.id.h_profile_setting) {
                Intent intent = new Intent(getApplication().getApplicationContext(), ProfileShowActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            } else if (id == R.id.h_app_setting) {
                Intent intent = new Intent(getApplication().getApplicationContext(), SettingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.h_drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*
          Following code blocks will set the navigation bar feature of this activity
          First, toolbar is set to action bar
          And then we set the listener to open the drawer when we press the button -
          -in the action bar.
          Lastly we also set the listener of what will happen when nav bar menu is pressed
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.h_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.h_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.h_nav_view);
        navigationView.setNavigationItemSelectedListener(new NavBarListener());
        /**/

        changeHomeActivityDisplayedFragment(new MainMenuFragment(), false);
//        if(this.getIntent() != null) {
//            if(this.getIntent().getIntExtra("flag", Navigator.ERROR_CODE) == Navigator.FROM_LOGIN_ACTIVITY) {
//                changeHomeActivityDisplayedFragment(new MainMenuFragment(), false);
//            } else if(this.getIntent().getIntExtra("flag", Navigator.ERROR_CODE) == Navigator.FROM_SETTING_ACTIVITY) {
//                Intent intent = new Intent(this, LoginActivity.class);
//                intent.putExtra("flag", Navigator.FROM_HOME_ACTIVITY);
//                startActivity(intent);
//                finish();
//            }
//        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.h_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void changeHomeActivityDisplayedFragment(Fragment intendedFragment, boolean isAddedToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
        fragmentTransaction.replace(R.id.h_fragment_frame, intendedFragment);
        if(isAddedToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    public HomeActivity popBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate();

        return this;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        Toast.makeText(this, id+"", Toast.LENGTH_LONG).show();
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
