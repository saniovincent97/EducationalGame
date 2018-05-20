package com.example.svinc.educationalgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LeaderboardActivity extends AppCompatActivity {

    TextView hi_score, hi_score2, hi_score3, hi_score4;
    int lastScore;
    int score1,score2,score3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        hi_score = findViewById(R.id.tv_score1);
        hi_score2 = findViewById(R.id.tv_score2);
        hi_score3 = findViewById(R.id.tv_score3);
        hi_score4 = findViewById(R.id.tv_score4);

        SharedPreferences preferences = getSharedPreferences("RIGHT_ANSWER_COUNT", 0);
        lastScore = preferences.getInt("lastScore", 0);
        score1 = preferences.getInt("score1", 0);
        score2 = preferences.getInt("score2", 0);
        score3 = preferences.getInt("score3", 0);

        if(lastScore > score3){
            score3 = lastScore;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("score3",score3);
            editor.apply();
        }

        if(lastScore > score2){
            int temp = score2;
            score2 = lastScore;
            score3 = temp;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("score3",score3);
            editor.putInt("score2",score2);
            editor.apply();
        }

        if(lastScore > score1){
            int temp = score1;
            score1 = lastScore;
            score2 = temp;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("score2",score2);
            editor.putInt("score1",score1);
            editor.apply();
        }


        hi_score.setText("Last Score: " + lastScore);
        hi_score2.setText("1. " + score1 + " planets discovered" );
        hi_score3.setText("2. " + score2 + " planets discovered");
        hi_score4.setText("3. " + score3 + " planets discovered");



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(intent);
        finish();
    }
}
