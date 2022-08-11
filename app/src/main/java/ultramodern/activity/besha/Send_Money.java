package ultramodern.activity.besha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Send_Money extends AppCompatActivity implements View.OnClickListener{

    Button greenarrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send__money);

        greenarrow=findViewById(R.id.button7);
        greenarrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==greenarrow){
            Intent intent = new Intent(getApplicationContext(),Send_Money_Set_Amount.class);
            startActivity(intent);
        }

    }
}