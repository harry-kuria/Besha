package ultramodern.activity.besha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class NewGoal extends AppCompatActivity implements View.OnClickListener{

    EditText nameInput,amountInput,categoryInput,frominput,toInput,frequencyInput;
    Button creategoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goal);

        nameInput = findViewById(R.id.nameInputgoals);
        amountInput = findViewById(R.id.amountInputgoals);
        categoryInput = findViewById(R.id.categoryInputgoals);
        frominput = findViewById(R.id.fromInputgoals);
        toInput = findViewById(R.id.toInputgoals);
        frequencyInput = findViewById(R.id.frequencyInputgoals);
        creategoal=findViewById(R.id.addgoal);

        creategoal.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==creategoal){
            addToDatabase();
        }
    }
    public void addToDatabase(){
        String name = nameInput.getText().toString();
        String amount = amountInput.getText().toString();
        String category = categoryInput.getText().toString();
        String from = frominput.getText().toString();
        String to = toInput.getText().toString();
        String frequency = frequencyInput.getText().toString();

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("Name",name);
        hashMap.put("Amount",amount);
        hashMap.put("Category",category);
        hashMap.put("From",from);
        hashMap.put("To",to);
        hashMap.put("Frequency",frequency);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Goals").push();
        databaseReference.setValue(hashMap);
    }
}