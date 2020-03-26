package com.example.temperatureconvert;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView out;
    EditText inp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        out = (TextView) findViewById(R.id.showText);  //获取界面内showText控件的文本
        inp = (EditText) findViewById(R.id.inputText); //获取inputText中用户输入的文本
        String str = inp.getText().toString();  //并转换为String
        //System.out.print("input = " + str);

        Button button = (Button) findViewById(R.id.button);  //获取button控件
        Log.i("mian", "input" + str); //日志
        button.setOnClickListener(this);  //按钮按下后调用当前onClick方法进行响应
        //button.setOnClickListener(new View.OnClickListener() {
            //用匿名类的方式处理事件
        //接口要生成一个类之后才能创建对象
        //        @Override
        //       public void onClick(View v) {
        //           Log.i("mail", "onClick called...");
        //            String str = inp.getText().toString();  //并转换为String
        //            out.setText("Hello" + str);
        //        }
        //    });
    }
    @Override
    public void onClick(View v) {
        //用配置文件的方式处理事件
        //public void 方法名(View v) 包含此三要素可以直接在activityMain界面绑定button
        Log.i("click", "onClick");
        String C = inp.getText().toString();  //获取inputText中用户输入的文本并转换为String
        double F = (Integer.parseInt(C)) * 1.8 + 32;

        out.setText( "华氏度为：" + F );  //输出Hello + 输入的部分
    }
}
