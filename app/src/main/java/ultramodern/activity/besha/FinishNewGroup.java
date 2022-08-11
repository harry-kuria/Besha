package ultramodern.activity.besha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FinishNewGroup extends AppCompatActivity implements View.OnClickListener{

    Button fabfinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_new_group);

        fabfinish=findViewById(R.id.finishButton);
        fabfinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==fabfinish){
            Intent intent = new Intent(getApplicationContext(),Complete_new_Group.class);
            startActivity(intent);
        }
    }
}