package ultramodern.activity.besha;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
//import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.Objects;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private IntentFilter intentFilter;

    public static final String BroadCastStringForAction = "CheckInternet";

    ImageButton editProfileButton;
    ImageView noInternetImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);
        noInternetImage = findViewById(R.id.noInternetimage);


        intentFilter = new IntentFilter();
        intentFilter.addAction(BroadCastStringForAction);


        Intent serviceIntent = new Intent(this,MyService.class);
        startService(serviceIntent);

        noInternetImage.setVisibility(View.GONE);
        if (isOnline(getApplicationContext())){
            set_Visibility_ON();
            Toast.makeText(this, "internet connection", Toast.LENGTH_SHORT).show();
        }
        else {
            set_Visibility_OFF();
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
        }


        SharedPreferences sharedPreferences = getSharedPreferences("Register",MODE_PRIVATE);
        String username=sharedPreferences.getString("Username","");
        String contact=sharedPreferences.getString("Contact","");
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        

        DrawerLayout dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle t = new ActionBarDrawerToggle(this, dl,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        dl.addDrawerListener(t);
        t.syncState();

        NavigationView nv = (NavigationView) findViewById(R.id.navigationView);
        View headerView = nv.getHeaderView(0);
        ImageButton imageButton = headerView.findViewById(R.id.editProfileImageButton);
        TextView profilename = headerView.findViewById(R.id.profilename);
        TextView profileContact = headerView.findViewById(R.id.profilecontact);
        profilename.setText(username);
        profileContact.setText(contact);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(intent);
            }
        });
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.home_in_drawer:
                        Toast.makeText(MainActivity.this, "My Home",Toast.LENGTH_SHORT).show();break;
                    case R.id.statistics_in_drawer:
                        Toast.makeText(MainActivity.this, "Statistics",Toast.LENGTH_SHORT).show();break;
                    case R.id.plannedpayments_in_drawer:
                        Intent intent = new Intent(MainActivity.this,PlannedPayments.class);
                        startActivity(intent);
                    default:
                        return true;
                }


                return true;

            }
        });


        BottomNavigationView bottomnav = findViewById(R.id.bottomnav);
        ViewPager viewPager = findViewById(R.id.viewpager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        bottomnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.wallet:

                        break;

                    case R.id.piechart:
                        Intent intent = new Intent(getApplicationContext(),Budget.class);
                        startActivity(intent);
                        break;

                    case R.id.groups:
                        Intent intent1 = new Intent(getApplicationContext(),Groups.class);
                        startActivity(intent1);
                        break;
                }
                return true;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(myReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver,intentFilter);
    }

    public BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(android.content.Context context, Intent intent) {
            if (intent.getAction().equals(BroadCastStringForAction)){
                if (intent.getStringExtra("online_status").equals("true")){
                    set_Visibility_ON();
                }
                else {
                    set_Visibility_OFF();
                }
            }
        }
    };

    public boolean isOnline(android.content.Context c){
        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo !=null && networkInfo.isConnectedOrConnecting()){
            return true;
        }
        else {
            return false;
        }
    }

    public void set_Visibility_ON(){
        //noInternetImage.setVisibility(View.GONE);
        //binding.submit.setVisibility(View.VISIBLE);
        //binding.parent.setBackgroundColor(Color.WHITE);

    }

    public void set_Visibility_OFF(){
        //noInternetImage.setVisibility(View.VISIBLE);
        //noInternetImage.bringToFront();
        //binding.submit.setVisibility(View.GONE);
        //binding.parent.setBackgroundColor(Color.RED);

    }

    @Override
    public void onClick(View view) {
        if (view==editProfileButton){
            Intent intent = new Intent(getApplicationContext(),EditProfile.class);
            startActivity(intent);
        }
    }
    private void editProfileButtonActivity(){
        Intent intent = new Intent(getApplicationContext(),EditProfile.class);
        startActivity(intent);
    }


    //override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    //    if (toggle.onOptionsItemSelected(item)) {
    //        return true
    //    }
    //    return super.onOptionsItemSelected(item)
    //}
}