package ultramodern.activity.besha;

import android.content.Intent;
import android.graphics.Color;
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

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class BudgetFragment extends Fragment {
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    RecyclerView budgetList;
    Button addBudget;
    PieChart pieChart;
    public BudgetFragment() {
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_budget,container,false);
        addBudget = view.findViewById(R.id.addbudget);
        addBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),NewBudget.class);
                startActivity(intent);
            }
        });
        budgetList=view.findViewById(R.id.budgetListDisplay);
        budgetList.setLayoutManager(new LinearLayoutManager(getActivity()));
        pieChart = view.findViewById(R.id.pieChartGraph);
        pieChartActivity();
        fetch();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    private void pieChartActivity(){
        String percent1 = "40";
        String percent2= "25";
        String percent3 = "20";
        String percent4 = "15";
        pieChart.addPieSlice(new PieModel(
                "first",Integer.parseInt(percent1), Color.parseColor("#000000")
        ));
        pieChart.addPieSlice(new PieModel(
                "second",Integer.parseInt(percent2),Color.parseColor("#7FFF00")
        ));
        pieChart.addPieSlice(new PieModel(
                "third",Integer.parseInt(percent3),Color.parseColor("#00008B")
        ));
        pieChart.addPieSlice(new PieModel(
                "forth",Integer.parseInt(percent4),Color.parseColor("#FF0000")
        ));
        pieChart.startAnimation();
    }


    private void fetch() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Budget");
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
            protected void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i, @NonNull BudgetModel budgetModel) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Budget");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String name = String.valueOf(snapshot.child("Name").getValue());
                            String category = String.valueOf(snapshot.child("Category").getValue());
                            String amount = String.valueOf((snapshot.child("Amount").getValue()));
                            String fromDate = String.valueOf(snapshot.getValue());
                            String toDate = String.valueOf(snapshot.getValue());
                            String imageURL = String.valueOf(snapshot.getValue());
                            viewHolder.budgetName.setText(name);
                            viewHolder.budgetCategory.setText(category);
                            viewHolder.budgetitemAmount.setText(amount);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
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
        budgetList.setAdapter(firebaseRecyclerAdapter);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
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
