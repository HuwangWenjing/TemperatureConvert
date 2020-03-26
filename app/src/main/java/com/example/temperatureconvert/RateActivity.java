package com.example.temperatureconvert;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RateActivity extends AppCompatActivity {

    EditText rmb;
    TextView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        rmb = (EditText)findViewById(R.id.inputRMB);
        show = (TextView)findViewById(R.id.showText);
    }

    public void onClick(View btn){
        String str = rmb.getText().toString();
        float r =0;
        if(str.length() > 0) {
            r = Float.parseFloat(str);
        }else{
            //提示用户输入内容
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
        }

        float val;
        if(btn.getId() == R.id.Dollar){
            val = r * (1/6.7f);
            show.setText(String.valueOf(val));
        }else if(btn.getId() == R.id.EURO){
            val = r * (1/11.0f);
            show.setText(String.valueOf(val));
        }
        else{
            val = r * 500;
            show.setText(String.valueOf(val));
        }
    }
}
