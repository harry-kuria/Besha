package ultramodern.activity.besha;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

@SuppressWarnings("ALL")
public class BanksFragment extends Fragment {

    private IntentFilter intentFilter;

    public static final String BroadCastStringForAction = "CheckInternet";

    Button fab;
    CardView cardView2;
    HorizontalScrollView scrollView;
    ConstraintLayout layout;
    TextView noInternet;
    public BanksFragment() {
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_banks,container,false);

        intentFilter = new IntentFilter();
        intentFilter.addAction(BroadCastStringForAction);

        Intent serviceIntent = new Intent(getContext(),MyService.class);
        getActivity().startService(serviceIntent);

        noInternet = view.findViewById(R.id.textView19);
        scrollView = view.findViewById(R.id.bankScrollView);
        cardView2 = view.findViewById(R.id.cardView2);
        layout = view.findViewById(R.id.bankLayout);
        fab = view.findViewById(R.id.fab);
        fab.bringToFront();

        if (isOnline(requireActivity())){
            set_Visibility_ON();
        }
        else {
            set_Visibility_OFF();
        }

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        requireActivity().registerReceiver(myReceiver,intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        requireActivity().unregisterReceiver(myReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().registerReceiver(myReceiver,intentFilter);
    }

    public BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
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

    public boolean isOnline(Context c){
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
        noInternet.setVisibility(View.GONE);
        fab.setVisibility(View.VISIBLE);
        cardView2.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.VISIBLE);
        layout.setBackgroundColor(Color.WHITE);
        //binding.notConnected.setVisibility(View.GONE);
        //binding.submit.setVisibility(View.VISIBLE);
        //binding.parent.setBackgroundColor(Color.WHITE);

    }

    public void set_Visibility_OFF(){
        noInternet.setVisibility(View.VISIBLE);
        fab.setVisibility(View.GONE);
        cardView2.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);
        layout.setBackgroundColor(Color.RED);
        //binding.notConnected.setVisibility(View.VISIBLE);
        //binding.submit.setVisibility(View.GONE);
        //binding.parent.setBackgroundColor(Color.RED);

    }

}
