package ultramodern.activity.besha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Pay_Scancode_Enter_pin extends AppCompatActivity implements View.OnClickListener {

    Button greennextbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay__scancode__enter_pin);

        greennextbtn=findViewById(R.id.button28);
        greennextbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==greennextbtn){
            Intent intent = new Intent(getApplicationContext(),Pay_Scancode_Confirm.class);
            startActivity(intent);
        }

    }
}