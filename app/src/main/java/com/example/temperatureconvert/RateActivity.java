package com.example.temperatureconvert;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RateActivity extends AppCompatActivity {

    EditText rmb;
    TextView show;
    float dollarRate = 6.7f;
    float euroRate = 11.0f;
    float wonRate = 500f;
    public final String TAG = "RateActivity";
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
            show.setText(String.format("%2f",r/dollarRate));
        }else if(btn.getId() == R.id.EURO){
            show.setText(String.format("%2f",r/euroRate));
        }
        else{
            show.setText(String.format("%2f",r*wonRate));
        }
    }

    public void openOne(View btn) {
        //打开一个页面
        //Log.i("open", "openOne");
        //打开新页面
        //Intent hello = new Intent(this,ScoreActivity.class);
        //startActivity(hello);
        //打开网址
        //Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jd.com"));
        //startActivity(web);
        openAdd();
    }

    public void openAdd() {
        Intent add = new Intent(this,AddActivity.class);
        startActivity(add);
        //将变量存进去
        add.putExtra("dollarRate",dollarRate);
        add.putExtra("euroRate",euroRate);
        add.putExtra("wonRate",wonRate);
        Log.i(TAG,"openOne: dollarRate= " + dollarRate);
        Log.i(TAG,"openOne: euroRate= " + euroRate);
        Log.i(TAG,"openOne: wonRate= " + wonRate);

        startActivityForResult(add,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 2){
            /*bdl.putFloat("key_newDollar",newDollar);
            bdl.putFloat("key_newEuro",newEuro);
            bdl.putFloat("key_newWon",newWon);*/
            Bundle bundle = data.getExtras();
            dollarRate = bundle.getFloat("key_newDollar",0.1f);
            euroRate = bundle.getFloat("key_newEuro",0.1f);
            wonRate = bundle.getFloat("key_newWon",0.1f);

            Log.i(TAG, "onActivityResult: dollarRate=" + dollarRate);
            Log.i(TAG, "onActivityResult: euroRate=" + euroRate);
            Log.i(TAG, "onActivityResult: wonRate=" + wonRate);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.act_settings) {
            openAdd();
        }
        return super.onOptionsItemSelected(item);
    }
}
