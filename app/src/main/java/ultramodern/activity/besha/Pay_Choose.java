package ultramodern.activity.besha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Pay_Choose extends AppCompatActivity implements View.OnClickListener{

    Button scancodebutton, addbusinessnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay__choose);

        scancodebutton=findViewById(R.id.button27);
        addbusinessnumber=findViewById(R.id.button26);
        addbusinessnumber.setOnClickListener(this);
        scancodebutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==scancodebutton){
            Intent intent=new Intent(getApplicationContext(),Pay_Scan_Code.class);
            startActivity(intent);
        }
        if (view==addbusinessnumber){
            Intent intent = new Intent(getApplicationContext(),Pay_AddBusinessNumber.class);
            startActivity(intent);
        }
    }
}