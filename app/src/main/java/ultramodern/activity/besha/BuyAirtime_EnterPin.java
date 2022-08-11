package ultramodern.activity.besha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BuyAirtime_EnterPin extends AppCompatActivity implements View.OnClickListener {

    Button nextbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_airtime__enter_pin);

        nextbtn=findViewById(R.id.button19);
        nextbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==nextbtn){
            Intent intent = new Intent(getApplicationContext(),BuyAirtime_SelfConfirm.class);
            startActivity(intent);
        }
    }
}