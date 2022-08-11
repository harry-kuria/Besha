package ultramodern.activity.besha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Pay_AddBusinessNumber extends AppCompatActivity implements View.OnClickListener{

    Button greennextbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay__add_business_number);

        greennextbutton=findViewById(R.id.button31);
        greennextbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==greennextbutton){
            Intent intent=new Intent(getApplicationContext(),Pay_AddBusiness_EnterPin.class);
            startActivity(intent);
        }
    }
}