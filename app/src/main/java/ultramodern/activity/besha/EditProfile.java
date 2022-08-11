package ultramodern.activity.besha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import ultramodern.activity.besha.Model.Users;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {

    Button buttonRegisterProfile;
    EditText profileNameInput, passwordInputProfile,contactInput,emailInput;
    FirebaseAuth mAuth;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        preferences = getSharedPreferences("Register",MODE_PRIVATE);
        buttonRegisterProfile = findViewById(R.id.buttonregisterprofile);
        buttonRegisterProfile.setOnClickListener(this);
        profileNameInput = findViewById(R.id.profilenameinput);
        passwordInputProfile = findViewById(R.id.passwordinputprofile);
        contactInput = findViewById(R.id.contactinput);
        emailInput = findViewById(R.id.emailInput);
        mAuth = FirebaseAuth.getInstance();

    }
    private void RegisterActivity(){
        final String username = profileNameInput.getText().toString();
        String password = passwordInputProfile.getText().toString();
        String email = emailInput.getText().toString();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    assert user !=null;
                    String userId = user.getUid();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("username",username);
                    hashMap.put("id",userId);
                    hashMap.put("ImageURL","Default");
                    Users users = new Users();
                    users.setId(userId);
                    users.setUsername(username);
                    Log.d("TRACK", userId);
                    databaseReference.setValue(hashMap);
                    DatabaseReference reference =  FirebaseDatabase.getInstance().getReference("List of users");
                    HashMap<String, String> hashMap1 = new HashMap<>();
                    hashMap1.put(username,username);
                    reference.setValue(hashMap1);
                }
            }
        });


        preferences.edit().putBoolean("Registered",true);
    }

    @Override
    public void onClick(View view) {
        if (view==buttonRegisterProfile){
            if (profileNameInput.length()==0){
                profileNameInput.setError("This area can't be blank");
            }
            else if (passwordInputProfile.length()==0){
                passwordInputProfile.setError("This area can't be blank");
            }
            else if (emailInput.length()==0){
                emailInput.setError("This area can't be blank");
            }
            else {
                RegisterActivity();
                prefs();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                dialog();

            }
        }
    }
    private void dialog(){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("REGISTRATION");
        builder.setMessage("Registration Successful");
        AlertDialog dialog=builder.create();
        dialog.show();
        //builder.show();
    }
    private void prefs(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Username",profileNameInput.getText().toString());
        editor.putString("Contact",contactInput.getText().toString());
        editor.commit();
    }
}