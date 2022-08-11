package ultramodern.activity.besha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class NewBudget extends AppCompatActivity implements View.OnClickListener {

    Button createBudget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_budget);

        createBudget=findViewById(R.id.button4);
        createBudget.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==createBudget){
            //DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
            //databaseAccess.openDatabaseConnection();

            EditText name = findViewById(R.id.nameInput);
            EditText amount = findViewById(R.id.amountInput);
            EditText category = findViewById(R.id.categoryInput);
            EditText from = findViewById(R.id.fromInput);
            EditText to = findViewById(R.id.toInput);
            EditText frequency = findViewById(R.id.frequencyInput);

            String nameData = name.getText().toString();
            String amountData = amount.getText().toString();
            String categoryData = category.getText().toString();
            String fromData = from.getText().toString();
            String toData = to.getText().toString();
            String frequencyData = frequency.getText().toString();

            addToDatabase(nameData,amountData,categoryData,fromData,toData,frequencyData);

            //databaseAccess.closeDatabaseConnection();
            Toast.makeText(this, "Database updated", Toast.LENGTH_LONG).show();
        }
    }
    private void addToDatabase(String name,String amount,String category,String from,String destination,String frequency){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("Name",name);
        hashMap.put("Amount",amount);
        hashMap.put("Category",category);
        hashMap.put("From",from);
        hashMap.put("To",destination);
        hashMap.put("Frequency",frequency);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Budget");
        reference.setValue(hashMap);
    }
}