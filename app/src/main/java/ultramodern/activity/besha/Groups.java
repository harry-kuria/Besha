package ultramodern.activity.besha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Groups extends AppCompatActivity implements View.OnClickListener {

    Button fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        fab = findViewById(R.id.fabgroups);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==fab){
            Intent intent = new Intent(getApplicationContext(),NewGroup.class);
            startActivity(intent);
        }
    }
}