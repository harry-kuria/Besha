package ultramodern.activity.besha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BuyAirtime_Amount2 extends AppCompatActivity implements View.OnClickListener{

    private IntentFilter intentFilter;

    public static final String BroadCastStringForAction = "CheckInternet";

    Button nextbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_airtime__amount2);

        intentFilter = new IntentFilter();
        intentFilter.addAction(BroadCastStringForAction);

        Intent serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);

        nextbtn=findViewById(R.id.button25);
        nextbtn.setOnClickListener(this);

        if (isOnline(getApplicationContext())) {
            set_Visibility_ON();
        } else {
            set_Visibility_OFF();
        }

    }

    @Override
    public void onClick(View view) {
        if (view==nextbtn){
            Intent intent = new Intent(getApplicationContext(),BuyAirtime_EnterPin.class);
            startActivity(intent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        registerReceiver(myReceiver, intentFilter);
    }

    public BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BroadCastStringForAction)) {
                if (intent.getStringExtra("online_status").equals("true")) {
                    set_Visibility_ON();
                } else {
                    set_Visibility_OFF();
                }
            }
        }
    };

    public boolean isOnline(Context c) {
        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public void set_Visibility_ON() {
        //noInternet.setVisibility(View.GONE);
        //toolbar.setVisibility(View.VISIBLE);
        //viewPager.setVisibility(View.VISIBLE);
        //tabLayout.setVisibility(View.VISIBLE);
        //bottomnav.setVisibility(View.VISIBLE);
        //layout.setBackgroundColor(Color.WHITE);
        //binding.notConnected.setVisibility(View.GONE);
        //binding.submit.setVisibility(View.VISIBLE);
        //binding.parent.setBackgroundColor(Color.WHITE);

    }

    public void set_Visibility_OFF() {
        //noInternet.setVisibility(View.VISIBLE);
        //toolbar.setVisibility(View.GONE);
        //viewPager.setVisibility(View.GONE);
        //tabLayout.setVisibility(View.GONE);
        //bottomnav.setVisibility(View.GONE);
        //layout.setBackgroundColor(Color.RED);
        //binding.notConnected.setVisibility(View.VISIBLE);
        //binding.submit.setVisibility(View.GONE);
        //binding.parent.setBackgroundColor(Color.RED);


    }

}