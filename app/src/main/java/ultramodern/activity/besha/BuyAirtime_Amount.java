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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class BuyAirtime_Amount extends AppCompatActivity implements View.OnClickListener {

    private IntentFilter intentFilter;

    public static final String BroadCastStringForAction = "CheckInternet";

    TextView noInternet;
    ConstraintLayout layout;
    EditText amount;
    Button nextbtn;
    RadioButton self,group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_airtime__amount);

        intentFilter = new IntentFilter();
        intentFilter.addAction(BroadCastStringForAction);

        Intent serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);

        layout = findViewById(R.id.buyAirtimeAmount);
        amount = findViewById(R.id.editTextPhone8);
        noInternet = findViewById(R.id.textView19);
        nextbtn=findViewById(R.id.button18);
        self=findViewById(R.id.selfradiobtn);
        group=findViewById(R.id.groupradiobtn);
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
            if (self.isChecked()){
                Intent intent = new Intent(getApplicationContext(),BuyAirtime_EnterPin.class);
                startActivity(intent);
            }
            if (group.isChecked()){
                Intent intent = new Intent(getApplication(),BuyAirtime_select_contacts.class);
                startActivity(intent);
            }
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
        noInternet.setVisibility(View.GONE);
        amount.setVisibility(View.VISIBLE);
        nextbtn.setVisibility(View.VISIBLE);
        self.setVisibility(View.VISIBLE);
        group.setVisibility(View.VISIBLE);
        layout.setBackgroundColor(Color.WHITE);
        //binding.notConnected.setVisibility(View.GONE);
        //binding.submit.setVisibility(View.VISIBLE);
        //binding.parent.setBackgroundColor(Color.WHITE);

    }

    public void set_Visibility_OFF() {
        noInternet.setVisibility(View.VISIBLE);
        amount.setVisibility(View.GONE);
        nextbtn.setVisibility(View.GONE);
        self.setVisibility(View.GONE);
        group.setVisibility(View.GONE);
        layout.setBackgroundColor(Color.RED);
        //binding.notConnected.setVisibility(View.VISIBLE);
        //binding.submit.setVisibility(View.GONE);
        //binding.parent.setBackgroundColor(Color.RED);


    }


}