package com.android.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity extends AppCompatActivity {
    private TextView warning,note;
    private EditText name;
    private Button account;
    private String userName;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        warning = findViewById(R.id.alert);
        account = findViewById(R.id.start);
        name = findViewById(R.id.userName);
        note = findViewById(R.id.note);

        warning.setText("");
        note.setText("Not: Her soru için 10sn toplam quiz için ise 45sn zaman tanımlanmıştır");

        account.setTextColor(Color.WHITE);
        account.setBackgroundColor(Color.BLACK);

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = name.getText().toString();
                if (!userName.isEmpty()) {
                    Intent intent = new Intent(AccountActivity.this, QuizActivity.class);
                    intent.putExtra("name",userName);
                    startActivity(intent);
                } else {
                    warning.setVisibility(View.VISIBLE);
                    warning.setText("Lütfen kullanıcı adı giriniz");
                    warning.setTextColor(Color.RED);
                }
            }
        });
    }

}