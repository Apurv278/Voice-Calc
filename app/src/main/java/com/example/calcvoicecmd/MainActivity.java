package com.example.calcvoicecmd;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    TextView t1, t2, t3, t4;
    Button btn;
    TextToSpeech tts;

    private int F_Num, S_Num, Result;
    private char otr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tts = new TextToSpeech(this, this);
        t1 = findViewById(R.id.first);
        t2 = findViewById(R.id.second);
        t3 = findViewById(R.id.third);
        t4 = findViewById(R.id.four);
        btn = findViewById(R.id.submit);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                startActivityForResult(i, 10);
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                startActivityForResult(i, 20);
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                startActivityForResult(i, 30);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Result = calculate();
                t4.setText(String.valueOf(Result));
                tts.speak(String.valueOf(Result), TextToSpeech.QUEUE_ADD, null);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            switch (requestCode){
                case 10:
                    int f = getResult(Objects.requireNonNull(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)));
                    if(f != -1){
                        F_Num = f;
                        t1.setText(String.valueOf(f));
                    } else {
                        Toast.makeText(getApplicationContext(), "Can you please repeat that again!", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 20:
                    f = getResult(Objects.requireNonNull(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)));
                    if(f != -1){
                        S_Num = f;
                        t2.setText(String.valueOf(f));
                    } else {
                        Toast.makeText(getApplicationContext(), "Can you please repeat that again!", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 30:
                    char t = getOtrResult(Objects.requireNonNull(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)));
                    if(t == '0'){
                        otr = t;
                        t3.setText(String.valueOf(t));
                    } else {
                        Toast.makeText(getApplicationContext(), "Can you please repeat that again!", Toast.LENGTH_LONG).show();
                    }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Did not recognize the speech!", Toast.LENGTH_LONG).show();
        }
    }

    private int getResult(ArrayList<String> result){
        for(String s : result){
            if(getIntFromTxt(s) != -1){
                return  getIntFromTxt(s);
            }
        }
        return  -1;
    }

    private char getOtrResult(ArrayList<String> result) {
        for(String s : result){
            if(getCharFromTxt(s) != '0'){
                return  getCharFromTxt(s);
            }
        }
        return  '0';
    }

    private int getIntFromTxt(String snum){
        switch (snum){
            case "zero":
                return 0;
            case "one":
                return 1;
            case "two":
                return 2;
            case "three":
                return 3;
            case "four":
                return 4;
            case "five":
                return 5;
            case "six":
                return 6;
            case "seven":
                return 7;
            case "eight":
                return 8;
            case "nine":
                return 9;
        }
        return -1;
    }

    private char getCharFromTxt(String sotr){
        switch (sotr){
            case "plus":
            case "add":
                return '+';
            case "minus":
            case "subtract":
                return '-';
            case "times":
            case "multiply":
                return '*';
            case "power":
            case "raised to":
                return '^';
            case "divided by":
            case "divide":
                return '/';
        }
        return '0';
    }

    private int calculate(){
        switch (otr){
            case '+':
                return F_Num + S_Num;
            case '-':
                return F_Num - S_Num;
            case '*':
                return F_Num * S_Num;
            case '/':
                return F_Num / S_Num;
            case '^':
                return F_Num ^ S_Num;
        }
        return -1;
    }
    @Override
    public void onInit(int status) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}