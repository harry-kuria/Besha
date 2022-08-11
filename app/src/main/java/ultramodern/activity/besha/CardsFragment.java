package ultramodern.activity.besha;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

@SuppressWarnings("ALL")
public class CardsFragment extends Fragment {

    TextView noInternet, allTransactions;
    Button floating;
    CardView cardView3;
    ConstraintLayout layout;
    private IntentFilter intentFilter;

    public static final String BroadCastStringForAction = "CheckInternet";

    public CardsFragment() {
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_cards,container, false);

        layout = view.findViewById(R.id.cardLayout);
        noInternet = view.findViewById(R.id.textView19);
        cardView3 = view.findViewById(R.id.cardView3);
        allTransactions = view.findViewById(R.id.textView8);
        intentFilter = new IntentFilter();
        intentFilter.addAction(BroadCastStringForAction);

        Intent serviceIntent = new Intent(getContext(),MyService.class);
        requireActivity().startService(serviceIntent);


        floating=view.findViewById(R.id.fab);
        ImageView imageView = view.findViewById(R.id.imageView);
        floating.bringToFront();
        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Add_Card.class);
                startActivity(intent);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SingleCard.class);
                startActivity(intent);
            }
        });

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
        floating.setVisibility(View.VISIBLE);
        cardView3.setVisibility(View.VISIBLE);
        allTransactions.setVisibility(View.VISIBLE);
        layout.setBackgroundColor(Color.WHITE);
        //binding.notConnected.setVisibility(View.GONE);
        //binding.submit.setVisibility(View.VISIBLE);
        //binding.parent.setBackgroundColor(Color.WHITE);

    }

    public void set_Visibility_OFF(){
        noInternet.setVisibility(View.VISIBLE);
        floating.setVisibility(View.GONE);
        cardView3.setVisibility(View.GONE);
        allTransactions.setVisibility(View.GONE);
        layout.setBackgroundColor(Color.RED);
        //binding.notConnected.setVisibility(View.VISIBLE);
        //binding.submit.setVisibility(View.GONE);
        //binding.parent.setBackgroundColor(Color.RED);

    }

}
