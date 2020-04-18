package com.example.temperatureconvert;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity {

    TextView score;
    TextView scoreB;
    String TAG = "show";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        score = (TextView) findViewById(R.id.scoreA);
        scoreB = (TextView)findViewById(R.id.scoreB);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String scoreA = ((TextView)findViewById(R.id.scoreA)).getText().toString();
        String scoreB = ((TextView)findViewById(R.id.scoreB)).getText().toString();

        outState.putString("teama_score", scoreA);
        outState.putString("teamb_score", scoreB);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String scorea = savedInstanceState.getString("teama_score");
        String scoreb = savedInstanceState.getString("teamb_score");

        Log.i(TAG, "onRestoreInstanceState: ");
        ((TextView)findViewById(R.id.scoreA)).setText(scorea);
        ((TextView)findViewById(R.id.scoreA)).setText(scoreb);
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
