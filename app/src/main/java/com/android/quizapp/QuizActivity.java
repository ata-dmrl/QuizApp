package com.android.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class QuizActivity extends AppCompatActivity {
    private TextView questionText, questionTime, totalTime ,questionNumber;
    private Button a_btn, b_btn, c_btn, d_btn;
    private JSONArray questionArray;
    private int avtiveQuestion = 0;
    private int trueNumber = 0;
    private CountDownTimer questionTimer, totalTimer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionText = findViewById(R.id.question);
        questionTime = findViewById(R.id.questionTime);
        totalTime = findViewById(R.id.totalTime);
        questionNumber = findViewById(R.id.questionNumber);

        a_btn = findViewById(R.id.button_a);
        b_btn = findViewById(R.id.button_b);
        c_btn = findViewById(R.id.button_c);
        d_btn = findViewById(R.id.button_d);

        a_btn.setBackgroundColor(Color.GRAY);
        b_btn.setBackgroundColor(Color.GRAY);
        c_btn.setBackgroundColor(Color.GRAY);
        d_btn.setBackgroundColor(Color.GRAY);

        View.OnClickListener secimDinleyici = v -> {
            int chosenIndex = -1;
            if (v == a_btn) chosenIndex = 0;
            else if (v == b_btn) chosenIndex = 1;
            else if (v == c_btn) chosenIndex = 2;
            else if (v == d_btn) chosenIndex = 3;
            answerControl(chosenIndex);
        };

        a_btn.setOnClickListener(secimDinleyici);
        b_btn.setOnClickListener(secimDinleyici);
        c_btn.setOnClickListener(secimDinleyici);
        d_btn.setOnClickListener(secimDinleyici);

        // JSON dosyasını oku
        questionArray = questionJsonRead();
        if (questionArray == null || questionArray.length() == 0) {
            questionText.setText("Soru bulunamadı");
            return;
        }

        totalTimeStart();
        nextQuestionGet();
    }

    // JSON DOSYAASINI OKUMAK İÇİN FONKSİYON
    private JSONArray questionJsonRead() {
        try {
            InputStream is = getAssets().open("questions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String jsonStr = new String(buffer, StandardCharsets.UTF_8);
            JSONObject root = new JSONObject(jsonStr);
            return root.getJSONArray("quiz");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // QUİZ İÇİN AYRILAN SÜRE İÇİN FONKSİYON
    private void totalTimeStart() {
        totalTimer = new CountDownTimer(45100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int second = (int) millisUntilFinished / 1000;
                totalTime.setText("Toplam: 00:" + (second < 10 ? "0" + second : second));
            }

            @Override
            public void onFinish() {
                quizFinish();
            }
        };
        totalTimer.start();
    }

    //HER SORU İÇİN AYRILAN SÜRE İÇİN FONKSİYON
    private void questionTimeStart() {
        if (questionTimer != null) questionTimer.cancel();

        questionTimer = new CountDownTimer(10100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int second = (int) millisUntilFinished / 1000;
                questionTime.setText("Sure: 00:" + (second < 10 ? "0" + second : second));
            }

            @Override
            public void onFinish() {
                avtiveQuestion++;
                nextQuestionGet();
            }
        };
        questionTimer.start();
    }


    // SIRADAKİ SORUYU GETİRMEK İÇİN FONKSİYON
    private void nextQuestionGet() {
        if (avtiveQuestion >= questionArray.length()) {
            quizFinish();
            return;
        }

        try {
            JSONObject questionObject = questionArray.getJSONObject(avtiveQuestion);
            String text = questionObject.getString("question");
            JSONArray options = questionObject.getJSONArray("options");

            questionText.setText(text);
            a_btn.setText(options.getString(0));
            b_btn.setText(options.getString(1));
            c_btn.setText(options.getString(2));
            d_btn.setText(options.getString(3));


            questionNumber.setText((avtiveQuestion + 1) + " / " + questionArray.length());
            questionNumber.setTextColor(Color.parseColor("#B0B0B0"));
            questionTimeStart();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //SEÇİLEN CEVAPLARIN KONTROLÜ İÇİN FONKSİYON
    private void answerControl(int chosenIndex) {
        try {
            if (questionTimer != null) questionTimer.cancel();

            JSONObject questionObject = questionArray.getJSONObject(avtiveQuestion);
            int trueIndex = questionObject.getJSONArray("answer").getInt(0);

            if (chosenIndex == trueIndex) {
                trueNumber++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        avtiveQuestion++;
        nextQuestionGet();
    }

    // QUİZ BİTİŞİ VE SKOR TABLOSUNA VERİ GÖNDERİMİ İÇİN FONKSİYON
    private void quizFinish() {
        if (questionTimer != null)
            questionTimer.cancel();

        if (totalTimer != null)
            totalTimer.cancel();

        //SKOR TABLOSUNA VERİ GÖNDERİMİ
        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("true_number", trueNumber);
        intent.putExtra("total_question", questionArray.length());
        intent.putExtra("name",(getIntent().getStringExtra("name")));
        startActivity(intent);
        finish();
    }
}