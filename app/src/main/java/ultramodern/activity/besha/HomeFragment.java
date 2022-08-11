package ultramodern.activity.besha;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.bumptech.glide.Glide;

import java.util.Calendar;

@SuppressWarnings("ALL")
public class HomeFragment extends Fragment implements View.OnClickListener{
    StorageReference storageReference;
    private IntentFilter intentFilter;

    public static final String BroadCastStringForAction = "CheckInternet";

    Button fabhome, recievepaymentbtn, buyairtimebtn, paybtn, sendmoneybtn, buyairtimeicon, recievepaymenticon, payicon, sendmoneyicon;
    CardView popup, cardView;
    RecyclerView cardlist;
    DatabaseReference reference;
    ConstraintLayout layout;
    TextView transactions,noInternet;

    private static final String TAG = "Glide";
    public HomeFragment() {

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.fragment_home,container,false);
        layout = view.findViewById(R.id.homeLayout);

        intentFilter = new IntentFilter();
        intentFilter.addAction(BroadCastStringForAction);

        Intent serviceIntent = new Intent(getContext(),MyService.class);
        getActivity().startService(serviceIntent);




        TextView datetoday = view.findViewById(R.id.textView7);
        noInternet = view.findViewById(R.id.textView19);
        layout = view.findViewById(R.id.homeLayout);
        transactions= view.findViewById(R.id.textView8);
        cardView = view.findViewById(R.id.cardView);
        fabhome = view.findViewById(R.id.fabhome);
        recievepaymentbtn = view.findViewById(R.id.buttonrecievepayment);
        buyairtimebtn = view.findViewById(R.id.buttonbuyairtime);
        paybtn = view.findViewById(R.id.buttonpayhome);
        sendmoneybtn = view.findViewById(R.id.sendmoneybutton);
        buyairtimeicon = view.findViewById(R.id.iconbuyairtime);
        recievepaymenticon = view.findViewById(R.id.iconrecievepayment);
        payicon = view.findViewById(R.id.iconpay);
        sendmoneyicon = view.findViewById(R.id.sendmoneyicon);
        cardlist = view.findViewById(R.id.cardlist);
        popup = view.findViewById(R.id.imagepopup);
        cardlist.setLayoutManager(new LinearLayoutManager(getActivity()));
        popupContent();

        //setting button listeners
        fabhome.setOnClickListener(this);
        recievepaymentbtn.setOnClickListener(this);
        buyairtimebtn.setOnClickListener(this);
        paybtn.setOnClickListener(this);
        sendmoneybtn.setOnClickListener(this);
        buyairtimeicon.setOnClickListener(this);
        recievepaymenticon.setOnClickListener(this);
        payicon.setOnClickListener(this);
        sendmoneyicon.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int month=calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);
        String date=day+"/"+month+"/"+year;
        datetoday.setText("Today "+date);


        reference = FirebaseDatabase.getInstance().getReference("CardImages");
        reference.keepSynced(true);

        if (isOnline(getContext())){
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
        fabhome.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.VISIBLE);
        transactions.setVisibility(View.VISIBLE);
        layout.setBackgroundColor(Color.WHITE);
        //binding.notConnected.setVisibility(View.GONE);
        //binding.submit.setVisibility(View.VISIBLE);
        //binding.parent.setBackgroundColor(Color.WHITE);

    }

    public void set_Visibility_OFF(){
        noInternet.setVisibility(View.VISIBLE);
        fabhome.setVisibility(View.GONE);
        cardView.setVisibility(View.GONE);
        transactions.setVisibility(View.GONE);
        layout.setBackgroundColor(Color.RED);
        //binding.notConnected.setVisibility(View.VISIBLE);
        //binding.submit.setVisibility(View.GONE);
        //binding.parent.setBackgroundColor(Color.RED);

    }

    public void visible(){
        buyairtimebtn.setVisibility(View.VISIBLE);
        recievepaymentbtn.setVisibility(View.VISIBLE);
        paybtn.setVisibility(View.VISIBLE);
        sendmoneybtn.setVisibility(View.VISIBLE);
        buyairtimeicon.setVisibility(View.VISIBLE);
        recievepaymenticon.setVisibility(View.VISIBLE);
        payicon.setVisibility(View.VISIBLE);
        sendmoneyicon.setVisibility(View.VISIBLE);
    }
    public void invisible(){
        buyairtimebtn.setVisibility(View.GONE);
        recievepaymentbtn.setVisibility(View.GONE);
        paybtn.setVisibility(View.GONE);
        sendmoneybtn.setVisibility(View.GONE);
        buyairtimeicon.setVisibility(View.GONE);
        recievepaymenticon.setVisibility(View.GONE);
        payicon.setVisibility(View.GONE);
        sendmoneyicon.setVisibility(View.GONE);
    }
    @Override
    public void onClick(View view) {
        if (view==fabhome){
            visible();
        }
        if (view==sendmoneybtn){
            invisible();
            Intent intent = new Intent(getActivity(),Send_Money.class);
            startActivity(intent);
        }
        if (view==buyairtimebtn){
            invisible();
            Intent intent = new Intent(getActivity(),BuyAirtimeAction.class);
            startActivity(intent);
        }
        if (view==paybtn){
            invisible();
            popup.setVisibility(View.VISIBLE);
        }
    }

    private void popupContent(){
        DatabaseReference imageReference = FirebaseDatabase.getInstance().getReference("CardImages");
        FirebaseRecyclerOptions<ImageRetrieval> options = new FirebaseRecyclerOptions.Builder<ImageRetrieval>().setQuery(imageReference, new SnapshotParser<ImageRetrieval>() {
            @NonNull
            @Override
            public ImageRetrieval parseSnapshot(@NonNull DataSnapshot snapshot) {
                String ImageURL = String.valueOf(snapshot.getValue());
                return new ImageRetrieval(ImageURL);
            }
        }).build();
        FirebaseRecyclerAdapter<ImageRetrieval, ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ImageRetrieval, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolder holder, int position, @NonNull final ImageRetrieval model) {
                //Glide.with(requireActivity()).load(model.getImageURL()).into(holder.cardImage);
                //Picasso.with(requireActivity()).setLoggingEnabled(true);
                //Picasso.with(requireActivity()).load(model.getImageURL()).into(holder.cardImage);


                Log.d("TRACK","inflate success");
                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("CardImages");
                reference1.addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        if (snapshot.exists()){

                            Glide.with(requireActivity()).load(model.getImageURL()).into(holder.cardImage);
                            Glide.with(requireActivity()).load(model.getImageURL()).addListener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    Log.e(TAG, "Load failed", e);

                                    // You can also log the individual causes:
                                    assert e != null;
                                    for (Throwable t : e.getRootCauses()) {
                                        Log.e(TAG, "Caused by", t);
                                    }
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    return false;
                                }
                            }).into(holder.cardImage);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_layout,parent,false);

                return new ViewHolder(view);
            }
        };
        cardlist.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

        GlideBuilder builder = new GlideBuilder();
        builder.setLogLevel(Log.VERBOSE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView cardImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.cardImage);
        }
    }
}
