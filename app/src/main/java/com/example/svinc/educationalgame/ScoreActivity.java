package com.example.svinc.educationalgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {
    Button leaderbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        TextView resultLabel = findViewById(R.id.resultLabel);
        TextView scoreLabel = findViewById(R.id.scoreLabel);

        int score = getIntent().getIntExtra("RIGHT_ANSWER_COUNT", 0);
        SharedPreferences settings = getSharedPreferences("quizApp", Context.MODE_PRIVATE);
        int totalScore = settings.getInt("totalScore", 0);
        totalScore += score;

        resultLabel.setText("" + score);
        scoreLabel.setText("Knowledge Points Acquired: " + totalScore);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("totalScore", totalScore);
        editor.apply();


        leaderbtn = findViewById(R.id.leaderbtn);

        leaderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreActivity.this, LeaderboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void PostTwitter(View view ){
        Intent intent = new Intent(ScoreActivity.this, TweetCustomWebView.class);
        intent.putExtra("tweettext", "I discovered some new planets on galaxy explorer, can you beat that?");
        startActivityForResult(intent, 100);
    }
}
