package ultramodern.activity.besha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ultramodern.activity.besha.Model.SingleBankModel;

public class SingleBank extends AppCompatActivity {

    RecyclerView transactionlist;
    FirebaseRecyclerAdapter adapter;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_bank);

        Toolbar toolbar = findViewById(R.id.singleBankToolbar);
        setSupportActionBar(toolbar);
        reference=FirebaseDatabase.getInstance().getReference().child("Transactions");
        reference.keepSynced(true);
        transactionlist = findViewById(R.id.transactionlist);
        transactionlist.setLayoutManager(new LinearLayoutManager(this));

        fetch();
    }



    private void fetch() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Transactions");
        FirebaseRecyclerOptions<SingleBankModel> options = new FirebaseRecyclerOptions.Builder<SingleBankModel>().setQuery(reference, new SnapshotParser<SingleBankModel>() {
            @NonNull
            @Override
            public SingleBankModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                String service = String.valueOf(snapshot.child("services").getValue());
                String categories = String.valueOf(snapshot.child("categories").getValue());
                String amount = String.valueOf(snapshot.child("amount").getValue());
                String date = String.valueOf(snapshot.child("date").getValue());
                String time = String.valueOf(snapshot.child("time").getValue());
                return new SingleBankModel(service
                ,categories
                ,amount
                ,date
                ,time);

            }
        }).build();

        FirebaseRecyclerAdapter<SingleBankModel, ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<SingleBankModel, ViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull final ViewHolder holder, int position, @NonNull final SingleBankModel model) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String service = String.valueOf(snapshot.child("services").getValue());
                            String categories = String.valueOf(snapshot.child("categories").getValue());
                            String amount = String.valueOf(snapshot.child("amount").getValue());
                            String date = String.valueOf(snapshot.child("date").getValue());
                            String time = String.valueOf(snapshot.child("time").getValue());
                            holder.services.setText(service);
                            holder.amount.setText(amount);
                            holder.categories.setText(categories);
                            holder.date.setText(date);
                            holder.time.setText(time);
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
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlebankitem,parent,false);
                return new ViewHolder(view);
            }

            @Override
            public int getItemViewType(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }
        };
        transactionlist.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categories, services, time, date, amount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categories = itemView.findViewById(R.id.categoriestext);
            services = itemView.findViewById(R.id.otherservices);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.bankdate);
            amount = itemView.findViewById(R.id.bankamount);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.singlebankoptions,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.cardDetails:
                Toast.makeText(this, "View Details of card", Toast.LENGTH_SHORT).show();
                break;

            case R.id.removeCard:
                Toast.makeText(this, "Remove card", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}