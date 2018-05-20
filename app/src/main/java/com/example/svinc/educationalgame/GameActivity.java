package com.example.svinc.educationalgame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView countLabel;
    private TextView questionLabel;
    private Button answerBtn1;
    private Button answerBtn2;
    private Button answerBtn3;
    private Button answerBtn4;
    private ImageView planetView;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;
    static final private int QUIZ_COUNT = 9;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();
    String quizData [][] = {
            {"Pluto", "Pluto", "Mars", "Jupiter", "Neptune"},
            {"Neptune", "Neptune", "Moon", "Sun", "Mars"},
            {"Jupiter", "Jupiter", "Pluto", "Mercury", "Uranus"},
            {"Earth", "Earth", "Mars", "Mercury", "Moon"},
            {"Mars", "Mars", "Mercury", "Pluto", "Earth"},
            {"Mercury", "Mercury", "Sun", "Neptune", "Saturn"},
            {"Uranus", "Uranus", "Neptune", "Mars", "Jupiter"},
            {"Saturn", "Saturn", "Venus", "Mars", "Earth"},
            {"Venus", "Venus", "Pluto", "Neptune", "Uranus"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        countLabel = findViewById(R.id.countLabel);
        questionLabel = findViewById(R.id.questionLabel);
        answerBtn1 = findViewById(R.id.answerBtn1);
        answerBtn2 = findViewById(R.id.answerBtn2);
        answerBtn3 = findViewById(R.id.answerBtn3);
        answerBtn4 = findViewById(R.id.answerBtn4);
        planetView = findViewById(R.id.planetview);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                quizCount++;
                showNextQuiz();
            }
        });



        for (int i =0; i <quizData.length; i++){
            ArrayList<String> qArray = new ArrayList<>();
            qArray.add(quizData[i][0]);
            qArray.add(quizData[i][1]);
            qArray.add(quizData[i][2]);
            qArray.add(quizData[i][3]);
            qArray.add(quizData[i][4]);

            quizArray.add(qArray);
        }
        showNextQuiz();
    }

    public void showNextQuiz(){

        countLabel.setText("" + quizCount);
        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        ArrayList<String> quiz = quizArray.get(randomNum);
        questionLabel.setText(quiz.get(0));
        rightAnswer = quiz.get(1);


        if(rightAnswer.equals("Earth")){
            planetView.setBackgroundResource(R.drawable.earth_planet);
        } else if (rightAnswer.equals("Pluto")){
            planetView.setBackgroundResource(R.drawable.pluto_planet);
        } else if (rightAnswer.equals("Neptune")){
            planetView.setBackgroundResource(R.drawable.neptune_planet);
        } else if (rightAnswer.equals("Mars")){
            planetView.setBackgroundResource(R.drawable.mars_planet);
        } else if (rightAnswer.equals("Uranus")){
            planetView.setBackgroundResource(R.drawable.uranus_planet);
        } else if (rightAnswer.equals("Mercury")){
            planetView.setBackgroundResource(R.drawable.mercury_planet);
        } else if (rightAnswer.equals("Venus")){
            planetView.setBackgroundResource(R.drawable.venus_planet);
        } else if (rightAnswer.equals("Saturn")){
            planetView.setBackgroundResource(R.drawable.saturn_planet);
        } else {
            planetView.setBackgroundResource(R.drawable.jupiter_planet);
        }

        quiz.remove(0);
        Collections.shuffle(quiz);

        answerBtn1.setText(quiz.get(0));
        answerBtn2.setText(quiz.get(1));
        answerBtn3.setText(quiz.get(2));
        answerBtn4.setText(quiz.get(3));

        quizArray.remove(randomNum);

    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mShakeDetector);}

    public void checkAnswer(View view){
        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        String alertTitle;
        if(btnText.equals(rightAnswer)){
            alertTitle = "You found the right planet!";
            rightAnswerCount++;

        }else {
            alertTitle = "You found the wrong planet!";

        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage("Answer : " + rightAnswer);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (quizCount == QUIZ_COUNT){
                    Intent intent = new Intent(getApplicationContext(),ScoreActivity.class);
                    intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
                    SharedPreferences preferences = getSharedPreferences("RIGHT_ANSWER_COUNT",0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("lastScore", rightAnswerCount);
                    editor.apply();
                    startActivity(intent);

                } else {
                    quizCount++;
                    showNextQuiz();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();

    }
}
