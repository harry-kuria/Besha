package ultramodern.activity.besha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Pay_AddBusiness_EnterPin extends AppCompatActivity implements View.OnClickListener{

    Button greennextbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay__add_business__enter_pin);

        greennextbutton=findViewById(R.id.button32);
        greennextbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(),Pay_AddBusiness_Confirm.class);
        startActivity(intent);
    }
}