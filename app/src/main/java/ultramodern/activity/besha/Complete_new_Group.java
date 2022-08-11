package ultramodern.activity.besha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseException;

import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ultramodern.activity.besha.Adapter.MessageAdapter;
import ultramodern.activity.besha.Model.Chat;
import ultramodern.activity.besha.Model.Users;

public class Complete_new_Group extends AppCompatActivity implements View.OnClickListener{

    Button fab,paybtn, depobtn, borrowbtn, borrowicon, depoicon, payicon;
    CircleImageView sendtextbutton;
    EditText text_send;
    RecyclerView chatrecyclerView;
    MessageAdapter messageAdapter;
    List<Chat> mChat;
    FirebaseUser firebaseUser;



    public static final int MESSAGE_RIGHT=0;
    public static final int MESSAGE_LEFT=1;
    //private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) { ;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_new__group);
        Toast.makeText(this, "This area is still under development", Toast.LENGTH_LONG).show();

        fab = findViewById(R.id.fab_complete_group);
        paybtn = findViewById(R.id.button6);
        depobtn = findViewById(R.id.button5);
        borrowbtn = findViewById(R.id.button3);
        borrowicon = findViewById(R.id.button8);
        depoicon = findViewById(R.id.button9);
        payicon = findViewById(R.id.button10);
        sendtextbutton = findViewById(R.id.sendtextButton);
        text_send = findViewById(R.id.text_send);
        chatrecyclerView = findViewById(R.id.chatrecycler);

        chatrecyclerView.setLayoutManager(new LinearLayoutManager(this));



        fab.setOnClickListener(this);
        paybtn.setOnClickListener(this);
        depobtn.setOnClickListener(this);
        borrowbtn.setOnClickListener(this);
        borrowicon.setOnClickListener(this);
        depoicon.setOnClickListener(this);
        payicon.setOnClickListener(this);
        sendtextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = text_send.getText().toString();
                if (!message.equals("")){
                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    String userId = getIntent().getStringExtra("userId");
                    sendmessage(message,userId,firebaseUser.getUid());
                }
                else {
                    Toast.makeText(Complete_new_Group.this, "You can't send an empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final String user_id;
        Users users = new Users();
        //Intent intent = getIntent();
        //user_id = intent.getStringExtra("userId");
        user_id = users.getId();
        //assert user_id != null;
        //Log.d("TRACK",user_id);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);

                readMessage(firebaseUser.getUid(),user_id,users.getImageURL());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onClick(View view) {
        if (view==fab){
            paybtn.setVisibility(View.VISIBLE);
            depobtn.setVisibility(View.VISIBLE);
            borrowbtn.setVisibility(View.VISIBLE);
            borrowicon.setVisibility(View.VISIBLE);
            depoicon.setVisibility(View.VISIBLE);
            payicon.setVisibility(View.VISIBLE);
        }
        if (view==paybtn){
            Intent intent = new Intent(getApplicationContext(),Group_pay.class);
            startActivity(intent);
        }
        if (view==payicon){
            Intent intent = new Intent(getApplicationContext(),Group_pay_icon_activity.class);
            startActivity(intent);
        }
        if (view==depobtn){
            Intent intent = new Intent(getApplicationContext(),Group_deposit_button_activity.class);
            startActivity(intent);
        }
        if (view==depoicon){
            Intent intent = new Intent(getApplicationContext(),Group_depo_icon_activity.class);
            startActivity(intent);
        }
        if (view==borrowbtn){
            Intent intent = new Intent(getApplicationContext(),Group_deposit_button_activity.class);
            startActivity(intent);
        }
        if (view==borrowicon){
            Intent intent = new Intent(getApplicationContext(),Group_borrow_icon_activity.class);
            startActivity(intent);
        }


    }
    private void sendmessage(String message,String sender, String reciever){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("reciever",reciever);
        hashMap.put("message",message);

        Chat chat = new Chat();
        chat.setMessage(message);
        Log.d("TRACK",message);

        reference.child("Chats").push().setValue(hashMap);
    }

    private void readMessage(final String userId, final String myId, String imageURL){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        FirebaseRecyclerOptions<Chat> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Chat>().setQuery(databaseReference, new SnapshotParser<Chat>() {
            @NonNull
            @Override
            public Chat parseSnapshot(@NonNull DataSnapshot snapshot) {

                String message = String.valueOf(snapshot.child("message").getValue());
                String sender = String.valueOf(snapshot.child("sender").getValue());
                String receiver = String.valueOf(snapshot.child("reciever").getValue());
                return new Chat(message,sender,receiver);
            }
        }).build();
        FirebaseRecyclerAdapter<Chat,ViewHolder> firebaseRecyclerAdapter  = new FirebaseRecyclerAdapter<Chat,ViewHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i, @NonNull Chat chat) {
                if (getItemViewType(i) == MESSAGE_LEFT){
                    ((ReceiverViewHolder) viewHolder).showMessage.setText(chat.getMessage());
                }
                else {
                    ((SenderViewHolder) viewHolder).showMessage.setText(chat.getMessage());
                }
                //viewHolder.showMessage.setText(chat.getMessage());
                //if (chat.getReceiver().equals(userId) && chat.getSender().equals(myId) || chat.getReceiver()
                //        .equals(myId) && chat.getSender().equals(userId)){
                //    viewHolder.showMessage.setText(chat.getMessage());
                //    String text = chat.getMessage();
                //    Log.d("RECEIVED",text);
                //}



            }


            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == MESSAGE_LEFT){
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left,parent,false);
                    return new ReceiverViewHolder(view);
                }
                else {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right,parent,false);
                    return new SenderViewHolder(view);
                }


            }

            @Override
            public int getItemViewType(int position) {
                Chat chat = getItem(position);
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                assert firebaseUser != null;
                if (chat.getReceiver().equals(myId) && chat.getSender().equals(userId)){
                //if (chat.getReceiver().equals(myId) && chat.getSender().equals(userId)){
                    return MESSAGE_;
                }
                else {
                    return MESSAGE_RIGHT;
                }
                //return super.getItemViewType(position);
            }
        };
        chatrecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }
    public static class SenderViewHolder extends RecyclerView.ViewHolder {
        public TextView showMessage;
        public ImageView profilepic;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            showMessage=itemView.findViewById(R.id.showMessage);
            profilepic=itemView.findViewById(R.id.profilepic);
        }
        public void setText(String paramString){
            this.showMessage.setText(paramString);
        }
    }

    public static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        public TextView showMessage;
        public ImageView profilepic;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            showMessage=itemView.findViewById(R.id.showMessage);
            profilepic=itemView.findViewById(R.id.profilepic);
        }
        public void setText(String paramString){
            this.showMessage.setText(paramString);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }



}