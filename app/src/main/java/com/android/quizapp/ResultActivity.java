package com.android.quizapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ResultActivity extends AppCompatActivity {
    private TextView user,score;
    private Button finish;
    private double point;
    private int totalQuestion, trueNumber;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        user = findViewById(R.id.user_Name);
        score = findViewById(R.id.score);
        finish = findViewById(R.id.button1);

        finish.setText("BİTİR");
        finish.setTextColor(Color.WHITE);
        finish.setBackgroundColor(Color.RED);

        // DOĞRU VE TOPLAM SORU SAYISI VERİSİNİ AL
        totalQuestion = getIntent().getIntExtra("total_question", 1);
        trueNumber = getIntent().getIntExtra("true_number", 0);

        // SEÇİMLERE GÖRE PUALARI HESAPLA
        point = ((double)100 / totalQuestion) * trueNumber;
        user.setText("Sayın: " + getIntent().getStringExtra("name").toUpperCase(new Locale("tr","TR")));
        score.setText("PUAN : " + point);

        // PROGRAMI BİTİR
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }
}