package ultramodern.activity.besha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.database.core.Context;

import de.hdodenhof.circleimageview.CircleImageView;
import ultramodern.activity.besha.Adapter.UserAdapter;
import ultramodern.activity.besha.Model.UserId;
import ultramodern.activity.besha.Model.Users;

public class NewGroup extends AppCompatActivity implements View.OnClickListener {

    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    RecyclerView recyclerView;
    TextView username;
    Button fabfinishbutton;
    Context mContext;
    //String usernameInCache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);


        username = findViewById(R.id.username);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fabfinishbutton=findViewById(R.id.finishButton);
        fabfinishbutton.setOnClickListener(this);

        fetch();

    }



    private void fetch() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String Id_of_current_user = firebaseUser.getUid();
        SharedPreferences sharedPreferences = getSharedPreferences("Register",MODE_PRIVATE);
        final String usernameInCache = sharedPreferences.getString("Username", "");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        final FirebaseRecyclerOptions<Users> options = new FirebaseRecyclerOptions.Builder<Users>().setQuery(databaseReference, new SnapshotParser<Users>() {
            @NonNull
            @Override
            public Users parseSnapshot(@NonNull DataSnapshot snapshot) {
                String username = String.valueOf(snapshot.child("username").getValue());
                String imageURL = String.valueOf(snapshot.getValue());
                String id = String.valueOf(snapshot.getValue());
                return new Users(username,imageURL,id);
            }
        }).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolder holder, final int position, @NonNull final Users model) {
                holder.setText(model.getUsername());
                //final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                            //String username = String.valueOf(dataSnapshot.child("username").getValue());
                            //String imageURL = String.valueOf(dataSnapshot.getValue());
                            //String id = String.valueOf(dataSnapshot.getValue());
                            //Users users=new Users();
                            //String username = users.getUsername();
                            //String key = firebaseUser.getUid();
                            //users.setId(key);
//                            Log.d("TRACK",username);
                            //if (!users.getId().equals(firebaseUser.getUid())){
                            //    holder.username.setText(username);
                            //    holder.profilepic.setVisibility(View.GONE);
                            //}

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Users users = new Users();
                        String key = firebaseRecyclerAdapter.getRef(position).getKey();
                        Intent intent = new Intent(getApplicationContext(),Complete_new_Group.class);
                        intent.putExtra("userId",users.getId());
                        startActivity(intent);
                    }
                });
//                if (model.getImageURL().equals("default")){

  //              }
//                Log.e("MESSAGE",model.getUsername());
            }


            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
                return new ViewHolder(view);
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public CircleImageView profilepic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            profilepic = itemView.findViewById(R.id.profilepic);
        }
        public void setText(String paramString){
            this.username.setText(paramString);
        }
    }
    private void getId(){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                assert firebaseUser !=null;
                assert users != null;
                if (!users.getId().equals(firebaseUser.getUid())){
                    Toast.makeText(NewGroup.this, "Wow. thos is good na", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onClick(View view) {
        if (view==fabfinishbutton){
            Intent intent = new Intent(getApplicationContext(),FinishNewGroup.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }
}