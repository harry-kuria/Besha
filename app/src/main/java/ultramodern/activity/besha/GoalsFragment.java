package ultramodern.activity.besha;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GoalsFragment extends Fragment {
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    RecyclerView goalslist;
    Button addGoal;
    public GoalsFragment() {
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_goals,container,false);
        addGoal = view.findViewById(R.id.addgoal);
        addGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),NewGoal.class);
                startActivity(intent);
            }
        });
        goalslist = view.findViewById(R.id.goalslistRecycler);
        goalslist.setLayoutManager(new LinearLayoutManager(getActivity()));
        fetch();
        return view;
    }

    private void fetch() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Goals");
        FirebaseRecyclerOptions<BudgetModel> options = new FirebaseRecyclerOptions.Builder<BudgetModel>().setQuery(reference, new SnapshotParser<BudgetModel>() {
            @NonNull
            @Override
            public BudgetModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                Log.d("TRACK","layout inflated");
                String name = String.valueOf(snapshot.getValue());
                String category = String.valueOf(snapshot.getValue());
                String amount = String.valueOf((snapshot.getValue()));
                String fromDate = String.valueOf(snapshot.getValue());
                String toDate = String.valueOf(snapshot.getValue());
                String imageURL = String.valueOf(snapshot.getValue());

                return new BudgetModel(name,category,amount,fromDate,toDate,imageURL);
            }
        }).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BudgetModel, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolder holder, int position, @NonNull BudgetModel model) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Goals");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String name = String.valueOf(snapshot.child("Name").getValue());
                            String category = String.valueOf(snapshot.child("Category").getValue());
                            String amount = String.valueOf((snapshot.child("Amount").getValue()));
                            String fromDate = String.valueOf(snapshot.getValue());
                            String toDate = String.valueOf(snapshot.getValue());
                            String imageURL = String.valueOf(snapshot.getValue());
                            holder.budgetName.setText(name);
                            holder.budgetCategory.setText(category);
                            holder.budgetitemAmount.setText(amount);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                holder.budgetName.setText(model.getName());
                holder.budgetCategory.setText(model.getCategory());
                holder.budgetitemAmount.setText(model.getAmount());
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }


            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.budgetitem,parent,false);
                return new ViewHolder(view);
            }
        };
        goalslist.setAdapter(firebaseRecyclerAdapter);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView budgetName,budgetitemAmount,budgetCategory;
        private ImageView budgetItempic;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.budgetcard);
            budgetName = itemView.findViewById(R.id.budgetName);
            budgetitemAmount = itemView.findViewById(R.id.budgetitemamount);
            budgetCategory = itemView.findViewById(R.id.budgetCategory);
            budgetItempic = itemView.findViewById(R.id.budgetitempic);

        }
        public ViewHolder(LayoutInflater inflater,ViewGroup container){
            super(inflater.inflate(R.layout.budgetitem,container));

        }

    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }

}
