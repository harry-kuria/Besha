package ultramodern.activity.besha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Send_Money_Enter_Pin extends AppCompatActivity implements View.OnClickListener {

    Button nextbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send__money__enter__pin);

        nextbtn=findViewById(R.id.button12);
        nextbtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view==nextbtn){
            Intent intent = new Intent(getApplicationContext(),Send_Money_Confirm.class);
            startActivity(intent);
        }
    }
}