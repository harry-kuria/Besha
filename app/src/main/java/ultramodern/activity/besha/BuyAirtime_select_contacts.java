package ultramodern.activity.besha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BuyAirtime_select_contacts extends AppCompatActivity implements View.OnClickListener {

    Button nextarrowbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_airtime_select_contacts);

        nextarrowbtn=findViewById(R.id.button20);
        nextarrowbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==nextarrowbtn){
            Intent intent = new Intent(getApplicationContext(),BuyAirtime_ConfirmTransaction.class);
            startActivity(intent);
        }
    }
}