package ultramodern.activity.besha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import ultramodern.activity.besha.Model.SingleCardModel;

public class SingleCard extends AppCompatActivity {

    RecyclerView transactionlist;
    FirebaseRecyclerAdapter adapter;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_card);

        Toolbar toolbar = findViewById(R.id.singleCardToolbar);
        setSupportActionBar(toolbar);
        transactionlist = findViewById(R.id.cardTransactionlist);
        transactionlist.setLayoutManager(new LinearLayoutManager(this));
        reference = FirebaseDatabase.getInstance().getReference().child("Card Transactions");
        reference.keepSynced(true);
        Button sendmoney=findViewById(R.id.sendmoneybtn);
        sendmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),TopUpStep1.class);
                startActivity(intent);
            }
        });

        fetch();

    }

    private void fetch() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Card Transactions");
        FirebaseRecyclerOptions<SingleCardModel> options = new FirebaseRecyclerOptions.Builder<SingleCardModel>().setQuery(reference, new SnapshotParser<SingleCardModel>() {
            @NonNull
            @Override
            public SingleCardModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                String id = String.valueOf(snapshot.child("id").getValue());
                String categories = String.valueOf(snapshot.child("category").getValue());
                String amount = String.valueOf(snapshot.child("amount").getValue());
                String date = String.valueOf(snapshot.child("date").getValue());
                String time = String.valueOf(snapshot.child("time").getValue());
                return new SingleCardModel(id
                        ,categories
                        ,amount
                        ,date
                        ,time);
            }
        }).build();
        FirebaseRecyclerAdapter<SingleCardModel, ViewHolder> adapter = new FirebaseRecyclerAdapter<SingleCardModel, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolder holder, int position, @NonNull SingleCardModel model) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String id = String.valueOf(snapshot.child("id").getValue());
                            String category = String.valueOf(snapshot.child("category").getValue());
                            String amount = String.valueOf(snapshot.child("amount").getValue());
                            String date = String.valueOf(snapshot.child("date").getValue());
                            String time = String.valueOf(snapshot.child("time").getValue());
                            holder.id.setText(id);
                            holder.amount.setText(amount);
                            holder.category.setText(category);
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
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sindlecarditem,parent,false);
                return new ViewHolder(view);
            }
        };
        transactionlist.setAdapter(adapter);
        adapter.startListening();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView category;
        private TextView amount;
        private TextView id;
        private TextView time;
        private TextView date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.cardCategory);
            amount = itemView.findViewById(R.id.cardAmount);
            id = itemView.findViewById(R.id.cardId);
            time = itemView.findViewById(R.id.cardTime);
            date = itemView.findViewById(R.id.cardDate);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.singlecardoptions,menu);
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