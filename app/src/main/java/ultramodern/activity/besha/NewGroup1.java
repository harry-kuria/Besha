package ultramodern.activity.besha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import ultramodern.activity.besha.Adapter.UserAdapter;
import ultramodern.activity.besha.Model.Users;

public class NewGroup1 extends AppCompatActivity implements View.OnClickListener {

    //FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    //private UserAdapter adapter;
    private ArrayList<Users> list;
    RecyclerView recyclerView;
    TextView username;
    Button fabfinishbutton;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        username = findViewById(R.id.username);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list= new ArrayList<>();

        fabfinishbutton=findViewById(R.id.finishButton);
        fabfinishbutton.setOnClickListener(this);

        fetch();
        UserAdapter adapter = new UserAdapter(this,list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



    }



    private void fetch() {
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users username = snapshot.getValue(Users.class);
                list.add(username);

                //for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                //    Users username = dataSnapshot.getValue(Users.class);
                //    String imageURL = dataSnapshot.getValue(String.class);
                    //Users id = dataSnapshot.child("id").getValue(Users.class);
                    Log.d("TAG", String.valueOf(username));
                //    list.add(username);
                //}


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(eventListener);
    }

    @Override
    public void onClick(View view) {
        if (view==fabfinishbutton){
            Intent intent = new Intent(getApplicationContext(),FinishNewGroup.class);
            startActivity(intent);
        }
    }

}