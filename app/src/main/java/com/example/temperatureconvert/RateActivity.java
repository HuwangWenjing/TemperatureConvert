package com.example.temperatureconvert;

import android.content.Intent;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

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
        //SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE); //通常情况下都把配置文件做成私有的
        //SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this); //另一获取文件的方式，高版本可用；本方式只能获取一个配置文件

//        Log.i(TAG, "onCreate: sp dollarRate=" + dollarRate);
//        Log.i(TAG, "onCreate: sp euroRate=" + euroRate);
//        Log.i(TAG, "onCreate: sp wonRate=" + wonRate);

        //开启子线程
        int count = 0;
        while(count == 0){
            Thread t = new Thread(this);
            t.start(); //开启子线程之后调用run（）方法
            count += 1;
        }

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 5) {
                    Bundle bdl = (Bundle) msg.obj;
                    dollarRate = bdl.getFloat("dollar-rate");
                    euroRate = bdl.getFloat("euro-rate");
                    wonRate = bdl.getFloat("won-rate");

                    Log.i(TAG, "handleMessage: dollarRate" + dollarRate);
                    Log.i(TAG, "handleMessage: euroRate" + euroRate);
                    Log.i(TAG, "handleMessage: wonRate" + wonRate);

                    Toast.makeText(RateActivity.this,"汇率已经更新", Toast.LENGTH_SHORT).show();
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
//            SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putFloat("dollar_rate", dollarRate);
//            editor.putFloat("euro_rate", euroRate);
//            editor.putFloat("won_rate", wonRate);
//            editor.commit();
//            Log.i(TAG, "onActivityResult: 数据已保存到SharedPreferences");
        }
    }

    @Override
    public void run() {
        Log.i(TAG, "run: run()...");
//        for (int i = 1; i < 6; i++) {
//            Log.i(TAG, "run: i = " + i);
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        //用于保存获取的汇率
        Bundle bundle = new Bundle();
        //获取网络数据
//        URL url = null;
//        try{
//            url = new URL("http://hl.anseo.cn/");
//            HttpURLConnection http = (HttpURLConnection)url.openConnection();
//            InputStream in = http.getInputStream();
//
//            String html = inputStream2String(in);
//            Log.i(TAG, "run: html=" + html);
//            Document doc = Jsoup.parse(html);
//        }catch(MalformedURLException e){
//            e.printStackTrace();
//        }catch(IOException e){
//            e.printStackTrace();
//        }
        Document doc = null;
        try{
            doc = Jsoup.connect("http://www.usd-cny.com/").get();
            //doc = Jsoup.parse(html);
            Log.i(TAG, "run: "+ doc.title());
            Elements tables = doc.getElementsByTag("table");

            Element table4 = tables.get(0);
            //Log.i(TAG, "run: tables4 = " + table4);

            //获取TD中的数据
            Elements tds = table4.getElementsByTag("td");
            for(int i = 0; i<tds.size(); i++){
                Element td1 = tds.get(i); //币种
                Element td2 = tds.get(i+5); //汇率
                Log.i(TAG, "run: text=" + td1.text() + "==>" + td2.text());
                String str1 = td1.text();
                String val = td2.text();

                if("美元".equals(str1)) {
                    bundle.putFloat("dollar-rate", Float.parseFloat(val));
                }else if("欧元".equals(str1)){
                    bundle.putFloat("euro-rate", Float.parseFloat(val));
                }else if("韩国元".equals(str1)) {
                    bundle.putFloat("won-rate", Float.parseFloat(val));
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        //bundle中保存获取的汇率

        //获取Msg对象用于返回主线程
        Message msg = handler.obtainMessage(5);
        //msg.what = 5;
        //msg.obj = "hello from run()";
        msg.obj = bundle;
        handler.sendMessage(msg);

    }

//    private String inputStream2String(InputStream inputStream) throws IOException {
//        final int bufferSize = 1024;
//        final char[] buffer = new char[bufferSize];
//        final StringBuilder out = new StringBuilder();
//        Reader in = new InputStreamReader(inputStream, "UTF-8");
//        for( ; ; ) {
//            int rsz = in.read(buffer,0,buffer.length);
//            if( rsz < 0)
//                break;
//            out.append(buffer, 0, rsz);
//        }
//        return out.toString();
//    }

    public void count() {
        int count = 0;
        while(count == 0) {

        }
    }

}
