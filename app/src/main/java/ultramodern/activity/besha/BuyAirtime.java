package ultramodern.activity.besha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import android.widget.TextView;

@SuppressWarnings("ALL")
public class BuyAirtime extends AppCompatActivity implements View.OnClickListener{

    Button safaricomprovider;
    private IntentFilter intentFilter;
    TextView noInternet;
    Button buttom17;
    ConstraintLayout layout;

    public static final String BroadCastStringForAction = "CheckInternet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_airtime);

        layout = findViewById(R.id.buyAirtime);
        buttom17 = findViewById(R.id.button17);
        noInternet = findViewById(R.id.textView19);
        intentFilter = new IntentFilter();
        intentFilter.addAction(BroadCastStringForAction);

        Intent serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);

        safaricomprovider=findViewById(R.id.button17);
        safaricomprovider.setOnClickListener(this);

        if (isOnline(getApplicationContext())) {
            set_Visibility_ON();
        } else {
            set_Visibility_OFF();
        }

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
        noInternet.setVisibility(View.GONE);
        buttom17.setVisibility(View.VISIBLE);
        layout.setBackgroundColor(Color.WHITE);
        //binding.notConnected.setVisibility(View.GONE);
        //binding.submit.setVisibility(View.VISIBLE);
        //binding.parent.setBackgroundColor(Color.WHITE);

    }

    public void set_Visibility_OFF() {
        noInternet.setVisibility(View.VISIBLE);
        buttom17.setVisibility(View.GONE);
        layout.setBackgroundColor(Color.RED);
        //binding.notConnected.setVisibility(View.VISIBLE);
        //binding.submit.setVisibility(View.GONE);
        //binding.parent.setBackgroundColor(Color.RED);


    }


    @Override
    public void onStart() {
        super.onStart();
        registerReceiver(myReceiver, intentFilter);
    }

    @Override
    public void onClick(View view) {
        if (view==safaricomprovider){
            Intent intent = new Intent(getApplicationContext(),BuyAirtime_Amount.class);
            startActivity(intent);
        }
    }
}