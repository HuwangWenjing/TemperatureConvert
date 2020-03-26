package com.example.temperatureconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    TextView score;
    TextView scoreB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        score = (TextView) findViewById(R.id.scoreA);
        scoreB = (TextView)findViewById(R.id.scoreB);
    }

    public void btnAdd1(View btn) {
        if(btn.getId()== R.id.btn_1){  //多个按钮调用同一个方法可这样区分
            showScore(1);
        }else{
            showScore2(1);
        }
    }
    public void btnAdd2(View btn) {
        if(btn.getId()== R.id.btn_2){  //多个按钮调用同一个方法可这样区分
            showScore(2);
        }else{
            showScore2(2);
        }
    }
    public void btnAdd3(View btn) {
        if(btn.getId()== R.id.btn_3){  //多个按钮调用同一个方法可这样区分
            showScore(3);
        }else{
            showScore2(3);
        }
    }

    public void btnReset(View btn) {
        score.setText("0");
        scoreB.setText("0");
    }

    private void showScore(int inc) {
        Log.i("show", "inc=" + inc);
        String oldScore = (String)score.getText();
        int newScore = Integer.parseInt(oldScore) + inc;
        score.setText("" + newScore);
    }

    private void showScore2(int inc) {
        Log.i("show", "inc=" + inc);
        String oldScore = (String)score.getText();
        int newScore = Integer.parseInt(oldScore) + inc;
        scoreB.setText("" + newScore);
    }
}
