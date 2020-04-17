package com.example.temperatureconvert;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RateActivity extends AppCompatActivity implements Runnable {

    EditText rmb;
    TextView show;
    float dollarRate = 0.0f;
    float euroRate = 0.0f;
    float wonRate = 0.0f;
    public final String TAG = "RateActivity";
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        rmb = (EditText) findViewById(R.id.inputRMB);
        show = (TextView) findViewById(R.id.showText);

        //获得在sharedpreferences里保存的数据
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE); //通常情况下都把配置文件做成私有的
        //SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this); //另一获取文件的方式，高版本可用；本方式只能获取一个配置文件
        dollarRate = sharedPreferences.getFloat("dollar_rate", 0.0f);
        euroRate = sharedPreferences.getFloat("euro_rate", 0.0f);
        wonRate = sharedPreferences.getFloat("won_rate", 0.0f);

        Log.i(TAG, "onCreate: sp dollarRate=" + dollarRate);
        Log.i(TAG, "onCreate: sp euroRate=" + euroRate);
        Log.i(TAG, "onCreate: sp wonRate=" + wonRate);

        //开启子线程
        Thread t = new Thread(this);
        t.start(); //开启子线程之后调用run（）方法

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 5) {
                    String str = (String) msg.obj;
                    Log.i(TAG, "handleMessage: getMessage msg = " + str);
                    show.setText(str);
                }
                super.handleMessage(msg);
            }
        };
    }

    public void onClick(View btn) {
        String str = rmb.getText().toString();
        float r = 0;
        if (str.length() > 0) {
            r = Float.parseFloat(str);
        } else {
            //提示用户输入内容
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
        }

        float val;
        if (btn.getId() == R.id.Dollar) {
            show.setText(String.format("%2f", r / dollarRate));
        } else if (btn.getId() == R.id.EURO) {
            show.setText(String.format("%2f", r / euroRate));
        } else {
            show.setText(String.format("%2f", r * wonRate));
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
        Intent add = new Intent(this, AddActivity.class);
        startActivity(add);
        //将变量存进去
        add.putExtra("dollarRate", dollarRate);
        add.putExtra("euroRate", euroRate);
        add.putExtra("wonRate", wonRate);
        Log.i(TAG, "openOne: dollarRate= " + dollarRate);
        Log.i(TAG, "openOne: euroRate= " + euroRate);
        Log.i(TAG, "openOne: wonRate= " + wonRate);

        startActivityForResult(add, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            /*bdl.putFloat("key_newDollar",newDollar);
            bdl.putFloat("key_newEuro",newEuro);
            bdl.putFloat("key_newWon",newWon);*/
            Bundle bundle = data.getExtras();
            dollarRate = bundle.getFloat("key_newDollar", 0.1f);
            euroRate = bundle.getFloat("key_newEuro", 0.1f);
            wonRate = bundle.getFloat("key_newWon", 0.1f);

            Log.i(TAG, "onActivityResult: dollarRate=" + dollarRate);
            Log.i(TAG, "onActivityResult: euroRate=" + euroRate);
            Log.i(TAG, "onActivityResult: wonRate=" + wonRate);

            //将新设置的汇率写入SP
            SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("dollar_rate", dollarRate);
            editor.putFloat("euro_rate", euroRate);
            editor.putFloat("won_rate", wonRate);
            editor.commit();
            Log.i(TAG, "onActivityResult: 数据已保存到SharedPreferences");
        }
    }

    @Override
    public void run() {
        Log.i(TAG, "run: run()...");
        for (int i = 1; i < 6; i++) {
            Log.i(TAG, "run: i = " + i);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //获取Msg对象用于返回主线程
        Message msg = handler.obtainMessage(5);
        //msg.what = 5;
        msg.obj = "hello from run()";
        handler.sendMessage(msg);

        //获取网络数据
        URL url = null;
        try {
            url = new URL("http://hl.anseo.cn/");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();

            String html = inputStream2String(in);
            Log.i(TAG, "run: html = " + html);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "UTF-8");
        for( ; ; ) {
            int rsz = in.read(buffer,0,buffer.length);
            if( rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }
}
